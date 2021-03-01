package se.test.trustlydepositmanager.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@Entity
public class Customer {

    @Id
    Long id;

    String userUuid;

    String email;

    String firstName;

    String lastName;

    String mobilePhone;

    String currency;

    String country;

    String locale;


}
