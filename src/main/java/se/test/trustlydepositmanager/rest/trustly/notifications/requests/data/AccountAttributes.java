package se.test.trustlydepositmanager.rest.trustly.notifications.requests.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountAttributes {

    @JsonProperty("clearinghouse")
    String clearingHouse;

    @JsonProperty("bank")
    String bank;

    @JsonProperty("descriptor")
    String descriptor;

    @JsonProperty("lastdigits")
    String lastDigits;

    @JsonProperty("personid")
    String personId;

    @JsonProperty("name")
    String name;

    @JsonProperty("address")
    String Adress;

    @JsonProperty("zipcode")
    String zipCode;

    @JsonProperty("city")
    String city;
}
