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
import se.test.trustlydepositmanager.service.DepositService;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/responses")
public class NotificationController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DepositService depositService;

    @RequestMapping(value = "/credit", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<NotificationResponse> getCreditNotifications(
            @Valid @RequestBody NotificationRequest notificationRequest) {

        LOGGER.info("Recieved a credit notification from Trustly:" + notificationRequest);


        String signature = ""; // TODO: Fix

        // TODO: Handle information


        return ResponseEntity.ok(createNotificationResponse(ResponseStatus.OK,
                notificationRequest.getNotificationRequestParameters().getUuid(), signature, 
                notificationRequest.getMethod()));
    }



    private NotificationResponse createNotificationResponse(ResponseStatus responseStatus, String notificationUuid,
                                                            String signature, String method) {
        return NotificationResponse.builder()
                .result(NotificationResponseResult.builder()
                        .signature(signature)
                        .uuid(notificationUuid)
                        .method(method)
                        .data(NotificationResponseResultData.builder()
                                .status(responseStatus.name())
                                .build())
                        .build())
                .build();
    }

}
