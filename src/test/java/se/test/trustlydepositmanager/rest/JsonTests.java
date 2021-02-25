package se.test.trustlydepositmanager.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.Attributes;
import se.test.trustlydepositmanager.rest.trustly.responses.ResponseStatus;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.NotificationRequestParameters;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.data.CancelNotificationRequestData;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.data.DebitNotificationRequestData;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.data.PendingNotificationRequestData;
import se.test.trustlydepositmanager.rest.trustly.notifications.responses.NotificationResponseResult;
import se.test.trustlydepositmanager.rest.trustly.notifications.responses.NotificationResponseResultData;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.DepositRequest;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.DepositRequestData;
import se.test.trustlydepositmanager.rest.trustly.requests.RequestParameters;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.NotificationRequest;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.data.CreditNotificationRequestData;
import se.test.trustlydepositmanager.rest.trustly.notifications.responses.NotificationResponse;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@ActiveProfiles("test")
@SpringBootTest
public class JsonTests {

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(25.40);
    private static final String ENDUSER_ID = "ENDUSER_ID_1";
    private static final String NOTIFICATION_URL = "localhost:8080/trust/api/deposit";
    private static final String MESSAGE_ID = "MESSAGE_ID_1";
    private static final String FIRSTNAME = "Test";
    private static final String LASTNAME = "Testsson";
    private static final String CURRENCY = "SEK";
    private static final String COUNTRY = "SE";
    private static final String LOCALE = "se_SE";
    private static final String EMAIL = "test.testsson@fejktest.se";
    private static final String MOCK_UUID = java.util.UUID.randomUUID().toString();
    private static final String SIGNATURE = "MOCK_SIGNATURE";
    private static final String ORDER_ID = "MOCK_ORDER_ID";
    private static final String NOTIFICATION_ID = "MOCK_ORDER_ID";

    private static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.now(); //ZonedDateTime.parse("2020-01-20 14:42:04.675645+01");
            //ZonedDateTime.of(2021, 1, 20, 14, 20,4,300, ZoneId.systemDefault());

    private static final String JSON_DEPOSIT_REQUEST =
            "{\"method\":\"Deposit\",\"params\":{\"Signature\":\"MOCK_SIGNATURE\"," +
                    "\"MOCK_UUID\":\"c63308cf-a46d-4de1-adab-4f37278e1b92\",\"Data\":{\"Username\":\"Testuser\"," +
                    "\"Password\":\"Test\",\"Attributes\":{\"Currency\":\"SEK\",\"Firstname\":\"Test\"," +
                    "\"Lastname\":\"Testsson\",\"Country\":\"SE\",\"Locale\":\"se_SE\"," +
                    "\"Email\":\"test.testsson@fejktest.se\",\"UnchangeableNationalIdentificationNumber\":1," +
                    "\"RequestDirectDebitMandate\":0,\"QuickDeposit\":0}," +
                    "\"NotificationURL\":\"localhost:8080/trust/api/deposit\",\"EndUserID\":\"ENDUSER_ID_1\"," +
                    "\"MessageID\":\"MESSAGE_ID_1\"}},\"version\":1.1}";

    private static final String JSON_CREDIT_NOTIFICATION_RESPONSE = "{\"result\":{\"signature\":\"MOCK_SIGNATURE\"," + "" +
            "\"uuid\":\"e05e8f83-9f82-44b3-a434-3b292bac84e8\",\"method\":\"Credit\",\"data\":{\"status\":\"OK\"}}," +
            "\"version\":\"1.1\"}";


    ObjectMapper objectMapper;

    @BeforeAll
    public static void init() {
        //objectMapper = new ObjectMapper();
    }


    @Test
    @DisplayName("Create JSON from Deposit Request, Happy Case")
    public void createDepositJsonString_HappyCaseTest() throws Exception{

        Attributes attributes = Attributes.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .currency(CURRENCY)
                .country(COUNTRY)
                .locale(LOCALE)
                .unchangeableNationalIdentificationNumber(true)
                .build();

        DepositRequestData depositRequestData = DepositRequestData.builder()
                .attributes(attributes)
                .endUserId(ENDUSER_ID)
                .messageId(MESSAGE_ID)
                .notificationURL(NOTIFICATION_URL)
                .password("Test")
                .userName("Testuser")
                .build();

        RequestParameters requestParameters = RequestParameters.builder()
                .data(depositRequestData)
                .uuid(MOCK_UUID)
                .signature(SIGNATURE)
                .build();

        DepositRequest depositRequest = DepositRequest.builder()
                .params(requestParameters)
                .build();

        System.out.println("Deposit Request: " + depositRequest + "\n\n");

        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String jSonDepositRequest = objectMapper.writeValueAsString(depositRequest);

        System.out.println("JSON Deposit Request: " + jSonDepositRequest);


    }

    @Test
    @DisplayName("Create JSON from Credit Notification Response, Happy Case")
    public void createCreditNotificationResponseJsonString_HappyCaseTest() throws Exception {


        NotificationResponseResultData notificationResponseData = NotificationResponseResultData.builder()
                .status(ResponseStatus.OK.name())
                .build();

        NotificationResponseResult result = NotificationResponseResult.builder()
                .signature(SIGNATURE)
                .uuid(MOCK_UUID)
                .method("credit")
                .data(notificationResponseData)
                .build();

        NotificationResponse creditNotificationResponse = NotificationResponse.builder()
                .result(result)
                .build();


        System.out.println("Credit Notification Response: " + creditNotificationResponse);

        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String jSonNotificationResponse = objectMapper.writeValueAsString(creditNotificationResponse);

        System.out.println("Credit Notification Response JSON String: " + jSonNotificationResponse);

        // Will do real unittests to verify result

    }

    @Test
    @DisplayName("Create Credit Notification Request from JSON, Happy Case")
    public void createCreditNotificationRequestFromJson_HappyCaseTest() throws Exception {

        NotificationRequest creditNotificationRequest = NotificationRequest.builder()
                .method("credit")
                .notificationRequestParameters(NotificationRequestParameters.builder()
                        .signature(SIGNATURE)
                        .uuid(MOCK_UUID)
                        .notificationRequestData(CreditNotificationRequestData.builder()
                                .amount(AMOUNT)
                                .currency(CURRENCY)
                                .messageId(MESSAGE_ID)
                                .orderId(ORDER_ID)
                                .endUserId(ENDUSER_ID)
                                .notificationId(NOTIFICATION_ID)
                                .timestamp(ZONED_DATE_TIME)
                                .build())
                        .build())
                .build();

        System.out.println("NotificationResponse:" + creditNotificationRequest);

        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String jsonCreditNotification = objectMapper.writeValueAsString(creditNotificationRequest);

        System.out.println("JSON Credit Notification: " + jsonCreditNotification);



        NotificationRequest notificationRequestFromJson = objectMapper.readValue(jsonCreditNotification, NotificationRequest.class);

        System.out.println("Reassembled object: " + notificationRequestFromJson);

    }

    @Test
    @DisplayName("Create Debit Notification Request from JSON, Happy Case")
    public void createDebitNotificationRequestFromJsonString_HappyCaseTest() throws Exception {


        NotificationRequest debitNotificationRequest = NotificationRequest.builder()
                .method("debit")
                .notificationRequestParameters(NotificationRequestParameters.builder()
                        .signature(SIGNATURE)
                        .uuid(MOCK_UUID)
                        .notificationRequestData(DebitNotificationRequestData.builder()
                                .amount(AMOUNT)
                                .currency(CURRENCY)
                                .messageId(MESSAGE_ID)
                                .orderId(ORDER_ID)
                                .endUserId(ENDUSER_ID)
                                .notificationId(NOTIFICATION_ID)
                                .timestamp(ZONED_DATE_TIME)
                                .build())
                        .build())
                .build();

        System.out.println("NotificationResponse:" + debitNotificationRequest);

        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String jsonCreditNotification = objectMapper.writeValueAsString(debitNotificationRequest);

        System.out.println("JSON Debit Notification: " + jsonCreditNotification);

        NotificationRequest notificationRequestFromJson = objectMapper.readValue(jsonCreditNotification, NotificationRequest.class);

        System.out.println("Reassembled object: " + notificationRequestFromJson);

    }

    @Test
    @DisplayName("Create Pending Notification Request from JSON string, Happy Case")
    public void createPendingNotificationRequestFromJsonString_HappyCaseTest() throws Exception {


        NotificationRequest debitNotificationRequest = NotificationRequest.builder()
                .method("pending")
                .notificationRequestParameters(NotificationRequestParameters.builder()
                        .signature(SIGNATURE)
                        .uuid(MOCK_UUID)
                        .notificationRequestData(PendingNotificationRequestData.builder()
                                .amount(AMOUNT)
                                .currency(CURRENCY)
                                .messageId(MESSAGE_ID)
                                .orderId(ORDER_ID)
                                .endUserId(ENDUSER_ID)
                                .notificationId(NOTIFICATION_ID)
                                .timestamp(ZONED_DATE_TIME)
                                .build())
                        .build())
                .build();

        System.out.println("NotificationResponse:" + debitNotificationRequest);

        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String jsonCreditNotification = objectMapper.writeValueAsString(debitNotificationRequest);

        System.out.println("JSON Pending Notification: " + jsonCreditNotification);

        NotificationRequest notificationRequestFromJson = objectMapper.readValue(jsonCreditNotification, NotificationRequest.class);

        System.out.println("Reassembled object: " + notificationRequestFromJson);

    }

    @Test
    @DisplayName("Create Cancel Notification Request from JSON, Happy Case")
    public void createCancelNotificationRequestFromJsonString_HappyCaseTest() throws Exception {


        NotificationRequest cancelNotificationRequest = NotificationRequest.builder()
                .method("cancel")
                .notificationRequestParameters(NotificationRequestParameters.builder()
                        .signature(SIGNATURE)
                        .uuid(MOCK_UUID)
                        .notificationRequestData(CancelNotificationRequestData.builder()
                                .messageId(MESSAGE_ID)
                                .orderId(ORDER_ID)
                                .endUserId(ENDUSER_ID)
                                .notificationId(NOTIFICATION_ID)
                                .timestamp(ZONED_DATE_TIME)
                                .build())
                        .build())
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String jsonCancelNotificationRequest = objectMapper.writeValueAsString(cancelNotificationRequest);

        System.out.println("JSON Pending Notification: " + jsonCancelNotificationRequest);

        NotificationRequest restoredCancelNotificationRequest = objectMapper.readValue(jsonCancelNotificationRequest, NotificationRequest.class);

        System.out.println("Reassembled object: " + restoredCancelNotificationRequest);

    }

}
