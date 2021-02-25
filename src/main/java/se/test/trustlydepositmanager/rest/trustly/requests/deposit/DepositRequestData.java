package se.test.trustlydepositmanager.rest.trustly.requests.deposit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import se.test.trustlydepositmanager.rest.trustly.requests.RequestData;


@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepositRequestData extends RequestData {

    @JsonProperty("NotificationURL")
    @NonNull
    String notificationURL;

    @JsonProperty("EndUserID")
    @NonNull
    String endUserId;

    @JsonProperty("MessageID")
    @NonNull
    String messageId;

}
