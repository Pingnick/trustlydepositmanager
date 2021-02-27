package se.test.trustlydepositmanager.service;


import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.io.pem.PemObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.test.trustlydepositmanager.service.security.KeyChainService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = KeyChainService.class)
@TestPropertySource(value = "classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest
public class KeyChainServiceTests {

    private static final String TEST_PUBLIC_KEY_FILE_PATH = "src/test/resources/keys/our_test_public_key.pem";

    private static final String PLAIN_TEXT = "Test text";

    private final Base64.Encoder base64Encoder = Base64.getEncoder();
    private final Base64.Decoder base64Decoder = Base64.getDecoder();

    @Autowired
    KeyChainService keyChainService;


    @BeforeAll
    static void init() {

    }

    @Test
    @DisplayName("Create Signature, Happy Case")
    public void createSignature_HappyCaseTest() throws Exception {

        String signature = createSignature(PLAIN_TEXT);

        assertThat(performSignatureVerification(PLAIN_TEXT, signature)).isTrue();

    }


    private String createSignature(final String plainText) throws Exception {
        try {
            final Signature signatureInstance = Signature.getInstance("SHA1withRSA");
            signatureInstance.initSign(keyChainService.getPrivateKey());
            signatureInstance.update(plainText.getBytes("UTF-8"));

            final byte[] signature = signatureInstance.sign();
            return base64Encoder.encodeToString(signature);
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            System.out.println("UnsupportedEncodingException | NoSuchAlgorithmException: " + e);
            throw new UnsupportedEncodingException("UnsupportedEncodingException | NoSuchAlgorithmException: " + e);

            //throw new TrustlySignatureException(e);
        }
        catch (final InvalidKeyException e) {
            System.out.println("Invalid private key: " + e);
            throw new InvalidKeyException("Invalid private key", e);
        }
        catch (final SignatureException e) {
            System.out.println("Failed to create signature: " + e);
            throw new SignatureException("Failed to create signature", e);
        }

    }

    private boolean performSignatureVerification(final String serializedData, final String responseSignature) {
        try {
            final byte[] signature = base64Decoder.decode(responseSignature);
            final Signature signatureInstance = Signature.getInstance("SHA1withRSA");
            signatureInstance.initVerify(getTestPublicKey(TEST_PUBLIC_KEY_FILE_PATH));

            final String expectedPlainText = serializedData;
            signatureInstance.update(expectedPlainText.getBytes("UTF-8"));
            return signatureInstance.verify(signature);
        }
        catch (final IOException e) {
            System.out.println("Failed to decode signature: " + e);
            //throw new TrustlySignatureException("Failed to decode signature", e);
        }
        catch (final NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException: " + e);
            //throw new TrustlySignatureException(e);
        }
        catch (final InvalidKeyException e) {
            System.out.println("Invalid public key" + e);
            //throw new TrustlySignatureException("Invalid public key", e);
        }
        catch (final SignatureException e) {

            System.out.println("Failed to verify signature" + e);
            //throw new TrustlySignatureException("Failed to verify signature", e);
        }


        return false;
    }

    private PublicKey getTestPublicKey(String  publicKeyPath) {

        PublicKey result=null;

        try {
            final File file = new File(publicKeyPath);

            final PEMParser pemParser = new PEMParser(new FileReader(file));

            final PemObject object = pemParser.readPemObject();

            final JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());

            final byte[] encoded = object.getContent();

            final SubjectPublicKeyInfo subjectPublicKeyInfo = new SubjectPublicKeyInfo(
                    ASN1Sequence.getInstance(encoded));

            result = converter.getPublicKey(subjectPublicKeyInfo);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
