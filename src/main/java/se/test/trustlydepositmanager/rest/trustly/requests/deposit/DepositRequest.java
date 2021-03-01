package se.test.trustlydepositmanager.rest.trustly.requests.deposit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import se.test.trustlydepositmanager.rest.trustly.requests.Request;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Jacksonized()
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepositRequest extends Request {


    public static abstract class DepositRequestBuilder<C extends DepositRequest, B extends DepositRequestBuilder<C, B>>
            extends RequestBuilder<C, B> {

        public DepositRequestBuilder() {

            method("Deposit");
        }

    }


}
