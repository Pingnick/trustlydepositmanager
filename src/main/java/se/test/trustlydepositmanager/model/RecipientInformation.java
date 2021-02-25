package se.test.trustlydepositmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipientInformation {

    @JsonProperty("Partytype")
    @Enumerated(EnumType.STRING)
    @NonNull
    PartyType partyType;

    @JsonProperty("Firstname")
    @NonNull
    String firstName;

    @JsonProperty("Lastname")
    @NonNull
    String lastName;

    @JsonProperty("CountryCode")
    @NonNull
    String countryCode; // char(2)

    @JsonProperty("CustomerID")
    @NonNull
    String customerId;

    @JsonProperty("Address")
    @NonNull
    String adress;

    @JsonProperty("DateOfBirth")
    String dateOfBirth;
}
