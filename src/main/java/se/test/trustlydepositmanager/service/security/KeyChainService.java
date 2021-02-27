package se.test.trustlydepositmanager.service.security;

import lombok.Getter;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import se.test.trustlydepositmanager.exceptions.APISignatureException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@Service
@PropertySource("classpath:application-test.properties")
public class KeyChainService {

    @Getter
    private PrivateKey privateKey;

    @Getter
    private PublicKey apiPublicKey;

    @Autowired
    public KeyChainService(@Value(value = "${tdm.api.security.private_key.path:Hello}") String privateKeyPath,
                           @Value(value = "${tdm.api.security.private_key.password:Hello2}") String privateKeyPassword,
                           @Value(value = "${tdm.api.security.public_key.path:Hello2}") String publicKeyPath
                           ) {

        loadApiPublicKey(publicKeyPath);
        loadServicePrivateKey(privateKeyPath, privateKeyPassword);

    }


    // TODO: Replace with: https://www.baeldung.com/java-read-pem-file-keys?
    private void loadApiPublicKey(String publicKeyPath) throws APISignatureException {

        try {
            final File file = new File(publicKeyPath);

            final PEMParser pemParser = new PEMParser(new FileReader(file));

            final PemObject object = pemParser.readPemObject();

            final JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());

            final byte[] encoded = object.getContent();

             final SubjectPublicKeyInfo subjectPublicKeyInfo = new SubjectPublicKeyInfo(
                     ASN1Sequence.getInstance(encoded));

             apiPublicKey = converter.getPublicKey(subjectPublicKeyInfo);

        } catch (FileNotFoundException e){
            throw new APISignatureException("N/A", "Unable to find public key file", e);
        } catch (IOException e) {
            throw new APISignatureException("Unable to load API's public key", e);
        }
    }

    void loadServicePrivateKey(final String privateKeyFileName, final String password) {

        try {
            final File servicePrivateKeyFile = new File(privateKeyFileName);

            final PEMParser pemParser = new PEMParser(new FileReader(servicePrivateKeyFile));

            final Object object = pemParser.readObject();

            final PEMDecryptorProvider pemDecryptorProvider = new JcePEMDecryptorProviderBuilder()
                    .build(password.toCharArray());

            final JcaPEMKeyConverter jcaPEMKeyConverter = new JcaPEMKeyConverter();

            final KeyPair keyPair;

            if (object instanceof PEMEncryptedKeyPair) {
                keyPair = jcaPEMKeyConverter.getKeyPair(((PEMEncryptedKeyPair) object).decryptKeyPair(pemDecryptorProvider));
            } else {
                keyPair = jcaPEMKeyConverter.getKeyPair((PEMKeyPair) object);
            }

            privateKey = keyPair.getPrivate();

        } catch (FileNotFoundException e) {
            throw new APISignatureException("N/A", "Unable to find private key file", e);
        } catch (PEMException e) {
            throw new APISignatureException("N/A", "Unable to convert private keypair", e);
        } catch (IOException e) {
            throw new APISignatureException(e);
        }
    }

}
