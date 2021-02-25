package se.test.trustlydepositmanager.rest.trustly.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@ToString
@SuperBuilder(toBuilder = true)
public abstract class DepositResponseData {

    @JsonProperty("signature")
    String signature;

    @JsonProperty("uuid")
    String uuid;

}
