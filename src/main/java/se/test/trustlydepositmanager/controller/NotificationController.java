package se.test.trustlydepositmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.test.trustlydepositmanager.rest.trustly.responses.ResponseStatus;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.NotificationRequest;
import se.test.trustlydepositmanager.rest.trustly.notifications.responses.NotificationResponseResult;
import se.test.trustlydepositmanager.rest.trustly.notifications.responses.NotificationResponseResultData;
import se.test.trustlydepositmanager.rest.trustly.notifications.responses.NotificationResponse;
import se.test.trustlydepositmanager.service.NotificationService;
import se.test.trustlydepositmanager.service.RequestService;
import se.test.trustlydepositmanager.service.security.SigningService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SigningService signingService;

    @Autowired
    NotificationService notificationService;

    @RequestMapping(value = "/credit", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<NotificationResponse> getCreditNotifications(
            @Valid @RequestBody NotificationRequest notificationRequest) {

        LOGGER.info("Recieved a credit notification from Trustly: " + notificationRequest);

        String correlationUuid = UUID.randomUUID().toString();
        String uuid = notificationRequest.getNotificationRequestParameters().getUuid();
        String method = notificationRequest.getMethod();
        String notificationId = notificationRequest.getNotificationRequestParameters().getNotificationRequestData()
                .getNotificationId();
        String messageId = notificationRequest.getNotificationRequestParameters().getNotificationRequestData()
                .getMessageId();

        LOGGER.info("Created CorrelationUuid: {} for notificationId: {} with messageId: {}", correlationUuid,
                notificationId, messageId);

        notificationService.handleCreditNotification(notificationRequest, correlationUuid);

        return ResponseEntity.ok(createNotificationResponse(ResponseStatus.OK, uuid, method));
    }



    private NotificationResponse createNotificationResponse(ResponseStatus responseStatus, String notificationUuid,
                                                            String method) {
        NotificationResponse notificationResponse = NotificationResponse.builder()
                .result(NotificationResponseResult.builder()
                        .uuid(notificationUuid)
                        .method(method)
                        .data(NotificationResponseResultData.builder()
                                .status(responseStatus.name())
                                .build())
                        .build())
                .build();

        signingService.signNotificationResponse(notificationResponse);

        return notificationResponse;
    }

}
