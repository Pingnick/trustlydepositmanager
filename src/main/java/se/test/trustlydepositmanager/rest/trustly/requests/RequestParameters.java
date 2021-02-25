package se.test.trustlydepositmanager.rest.trustly.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestParameters {

    @JsonProperty("Signature")
    @NonNull
    String signature;

    @JsonProperty("UUID")
    @NonNull
    String uuid;

    @JsonProperty("Data")
    @NonNull
    RequestData data;

    /*
    public abstract static class RequestParametersBuilder <C extends RequestParameters, D extends RequestParametersBuilder<C,D>> {

        public RequestParametersBuilder() {
            uuid(UUID.randomUUID().toString());
        }
    }
    */
}
