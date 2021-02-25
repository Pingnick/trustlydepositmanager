package se.test.trustlydepositmanager.rest.trustly.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
public abstract class Request {

    @JsonProperty("method")
    String method;

    @JsonProperty("params")
    @NonNull
    RequestParameters params;

    @JsonProperty("version")
    @Builder.Default
    final double version = 1.1;




}
