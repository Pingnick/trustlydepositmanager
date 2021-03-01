package se.test.trustlydepositmanager.service.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.test.trustlydepositmanager.exceptions.APISignatureException;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.NotificationRequest;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.NotificationRequestData;
import se.test.trustlydepositmanager.rest.trustly.notifications.responses.NotificationResponse;
import se.test.trustlydepositmanager.rest.trustly.notifications.responses.NotificationResponseResultData;
import se.test.trustlydepositmanager.rest.trustly.requests.Request;
import se.test.trustlydepositmanager.rest.trustly.requests.RequestData;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.DepositRequest;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.DepositRequestData;
import se.test.trustlydepositmanager.rest.trustly.responses.DepositResponse;
import se.test.trustlydepositmanager.rest.trustly.responses.DepositResultData;
import se.test.trustlydepositmanager.rest.trustly.responses.error.ErrorBody;
import se.test.trustlydepositmanager.rest.trustly.responses.error.ErrorData;
import se.test.trustlydepositmanager.rest.trustly.responses.error.ErrorResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;


@NoArgsConstructor
@AllArgsConstructor
@Service
public class SigningService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Getter
    @Value("${tdm.api.security.public_key.path}")
    private String publicKeyPath;

    @Getter
    @Value("${tdm.api.security.private_key.path}")
    private String privateKeyPath;

    @Getter
    @Value("${tdm.api.security.password}")
    private String password;

    @Getter
    @Value("${tdm.api.security.username}")
    private String userName;

    private final Base64.Encoder base64Encoder = Base64.getEncoder();
    private final Base64.Decoder base64Decoder = Base64.getDecoder();

    @Autowired
    private KeyChainService keyChainService;


    public void signNotificationResponse(final NotificationResponse notificationResponse) {
        String method = notificationResponse.getResult().getMethod();
        String uuid = notificationResponse.getResult().getUuid();

        NotificationResponseResultData data = notificationResponse.getResult().getData();

        String serializedData = serializeObject(method, uuid, data);

        String signature = createSignature(serializedData);

        notificationResponse.getResult().setSignature(signature);
    }

    public Request signDepositRequest(DepositRequest request) {

        String method = request.getMethod();
        String uuid = request.getParams().getUuid();

        DepositRequestData depositRequestData = (DepositRequestData)request.getParams().getData();

        String plainText = serializeObject(method, uuid , depositRequestData);

        String signature = createSignature(plainText);

        request.getParams().setSignature(signature);

        return request;
    }

    private String serializeObject(String method, String uuid, Object objectData) {
        return method + uuid + serializeDataForSigning(objectData);
    }

    private String serializeDataForSigning(Object objectData) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            String jsonDataString = objectMapper.writeValueAsString(objectData);

            return removeJsonCharacters(jsonDataString);
        } catch (JsonProcessingException e) {
            throw new APISignatureException("JSON processing error writing a string", e);
        }

    }

    // TODO: Regex
    private String removeJsonCharacters(String jsonString) {
        CharSequence charSequence1 = "\":{\"";
        CharSequence charSequence2 = "{\"";
        CharSequence charSequence3 = "\"}";
        CharSequence charSequence4 = "\":\"";
        CharSequence charSequence5 = "\",\"";
        CharSequence charSequence6 = "\":";
        CharSequence charSequence7 = "}";
        CharSequence charSequence8 = ",\"";

        return jsonString.replace(charSequence1, "").replace(charSequence2, "")
                .replace(charSequence3, "").replace(charSequence4, "")
                .replace(charSequence5, "").replace(charSequence6, "")
                .replace(charSequence7, "").replace(charSequence8, "");
    }


    private String createSignature(final String plainText) {
        try {
            final Signature signatureInstance = Signature.getInstance("SHA1withRSA");
            signatureInstance.initSign(keyChainService.getPrivateKey());
            signatureInstance.update(plainText.getBytes("UTF-8"));

            final byte[] signature = signatureInstance.sign();
            return base64Encoder.encodeToString(signature);
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new APISignatureException(e);
        }
        catch (final InvalidKeyException e) {
            throw new APISignatureException("Invalid private key", e);
        }
        catch (final SignatureException e) {
            throw new APISignatureException("Failed to create signature", e);
        }
    }

    public boolean verifyNotificationSignature(final NotificationRequest notificationRequest) {

        String method = notificationRequest.getMethod();
        String uuid = notificationRequest.getNotificationRequestParameters().getUuid();
        String signature = notificationRequest.getNotificationRequestParameters().getSignature();

        NotificationRequestData data = notificationRequest.getNotificationRequestParameters()
                .getNotificationRequestData();

        String serializedData = serializeObject(method, uuid, data);

        return performSignatureVerification(serializedData, signature);
    }



    public boolean verifyDepositResponseSignature(final DepositResponse depositResponse) throws Exception {

        String uuid = depositResponse.getResult().getUuid();
        String method = depositResponse.getResult().getMethod();
        DepositResultData data = depositResponse.getResult().getData();
        String signature = depositResponse.getResult().getSignature();

        String serializedData = serializeObject(method, uuid, data);

        return performSignatureVerification(serializedData, signature);
    }

    public boolean verifyErrorResponseSignature(final ErrorResponse errorResponse) throws Exception {

        ErrorBody errorBody = errorResponse.getErrorResult().getErrorBody();

        String uuid = errorBody.getUuid();
        String method = errorBody.getMethod();
        String signature = errorBody.getSignature();
        ErrorData errorData = errorBody.getErrorData();

        String serializedData = serializeObject(method, uuid, errorData);

        return performSignatureVerification(serializedData, signature);
    }

    private boolean performSignatureVerification(final String serializedData, final String responseSignature) {
        try {
            final byte[] signature = base64Decoder.decode(responseSignature);
            final Signature signatureInstance = Signature.getInstance("SHA1withRSA");
            signatureInstance.initVerify(keyChainService.getApiPublicKey());

            final String expectedPlainText = serializedData;
            signatureInstance.update(expectedPlainText.getBytes("UTF-8"));
            return signatureInstance.verify(signature);
        }
        catch (final IOException e) {
            throw new APISignatureException("Failed to decode signature", e);
        }
        catch (final NoSuchAlgorithmException e) {
            throw new APISignatureException(e);
        }
        catch (final InvalidKeyException e) {
            throw new APISignatureException("Invalid public key", e);
        }
        catch (final SignatureException e) {
            throw new APISignatureException("Failed to verify signature", e);
        }
    }
}
