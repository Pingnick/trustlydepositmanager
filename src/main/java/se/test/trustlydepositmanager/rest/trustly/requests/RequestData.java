package se.test.trustlydepositmanager.rest.trustly.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.Attributes;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class RequestData {

    @JsonProperty("Username")
    @NonNull
    String userName;

    @JsonProperty("Password")
    @NonNull
    String password;

    @JsonProperty("Attributes")
    @NonNull
    Attributes attributes;
}
