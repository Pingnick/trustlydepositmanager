package se.test.trustlydepositmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.test.trustlydepositmanager.repository.TransactionRepository;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.NotificationRequest;

@Service
public class NotificationService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    RestTemplate restTemplate;


    public boolean handleCreditNotification(NotificationRequest notificationRequest, String correlationsUuid) {

        // TODO: Update Transaction repository etc
        return false;
    }

    public boolean handleDebitNotification(NotificationRequest notificationRequest) {
        return false;
    }


}
