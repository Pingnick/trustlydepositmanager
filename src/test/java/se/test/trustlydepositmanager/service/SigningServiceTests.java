package se.test.trustlydepositmanager.service;


import com.fasterxml.jackson.databind.ObjectMapper;
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
import se.test.trustlydepositmanager.rest.trustly.requests.RequestParameters;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.Attributes;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.DepositRequest;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.DepositRequestData;
import se.test.trustlydepositmanager.service.security.KeyChainService;
import se.test.trustlydepositmanager.service.security.SigningService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.Base64;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SigningService.class, KeyChainService.class})
@TestPropertySource(value = "classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest
public class SigningServiceTests {

    private static final String MOCK_UUID = java.util.UUID.randomUUID().toString();

    // Parameters:
    private static final String USER_NAME = "devcode";
    private static final String PASSWORD = "change_this_gef4D37ypL";
    private static final String ENDUSER_ID = "ENDUSER_ID_1";
    private static final String NOTIFICATION_URL = "localhost:8080/trustly/api/notifications";
    private static final String SUCCESS_URL = "localhost:8080/trustly/web/success";
    private static final String FAIL_URL = "localhost:8080/trustly/web/fail";
    private static final String MESSAGE_ID = "MESSAGE_ID_1";
    private static final String FIRSTNAME = "Test";
    private static final String LASTNAME = "Testsson";
    private static final String CURRENCY = "SEK";
    private static final String COUNTRY = "SE";
    private static final String LOCALE = "se_SE";
    private static final String EMAIL = "test.testsson@fejktest.se";
    private static final String IP = "123.123.123.123";
    private static final String MOBIL_PHONE = "+46709876543";
    private static final String NATIONAL_IDENTIFICATION_NUMBER = "670912-1234";

    @Autowired
    SigningService signingService;


    @BeforeAll
    static void init() {

    }

    @Test
    @DisplayName("Prepare string for signing")
    public void signDepositRequest_HappyCaseTest() throws Exception {
        RequestParameters requestParameters = createDepositRequest();

        DepositRequest depositRequest = DepositRequest.builder()
                .params(requestParameters)
                .build();

        depositRequest = (DepositRequest) signingService.signDepositRequest(depositRequest);

        System.out.println("A signed deposit request: " + depositRequest);


    }






    private RequestParameters createDepositRequest() {

        return RequestParameters.builder()
                .uuid(MOCK_UUID)
                .data(DepositRequestData.builder()
                        .endUserId(ENDUSER_ID)
                        .messageId(MESSAGE_ID)
                        .userName(USER_NAME)
                        .password(PASSWORD)
                        .notificationURL(NOTIFICATION_URL)
                        .attributes(Attributes.builder()
                                .country(COUNTRY)
                                .locale(LOCALE)
                                .currency(CURRENCY)
                                .ip(IP)
                                .mobilePhone(MOBIL_PHONE)
                                .firstName(FIRSTNAME)
                                .lastName(LASTNAME)
                                .email(EMAIL)
                                .nationalIdentificationNumber(NATIONAL_IDENTIFICATION_NUMBER)
                                .successURL(SUCCESS_URL)
                                .failURL(FAIL_URL)
                                .build()
                        )
                        .build())
                .build();

    }

}
