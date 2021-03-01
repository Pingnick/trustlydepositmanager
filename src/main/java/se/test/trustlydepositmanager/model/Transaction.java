package se.test.trustlydepositmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@Entity
public class Transaction {

    @Id
    String id;

    String userId;

    String amount;

    String currency;

    String country;

    String locale;

    String uuid;


    String notificationId;

    String timestamp;

    String ip;


    String poddName;
    String poddLink;
    int poddLength;
}
