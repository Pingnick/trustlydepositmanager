package se.test.trustlydepositmanager.rest.trustly.notifications.responses;

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

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponseResult {

    @NonNull
    @JsonProperty("signature")
    String signature;

    @NonNull
    @JsonProperty("uuid")
    String uuid;

    @NonNull
    @JsonProperty("method")
    String method;

    @NonNull
    @JsonProperty("data")
    NotificationResponseResultData data;
}
