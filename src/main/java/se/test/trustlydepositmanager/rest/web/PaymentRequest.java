package se.test.trustlydepositmanager.rest.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PaymentRequest {

    @JsonProperty("EndUserID")
    String endUserUuid;

    @JsonProperty("PaymentRequestUuid")
    String paymentRequestUuid;

    @JsonProperty("OrderID")
    String orderId;

    @JsonProperty("Amount")
    String amount;

    @JsonProperty("ShippingAddressCountry")
    String shippingAddressCountry;

    @JsonProperty("ShippingAddressPostalCode")
    String shippingAddressPostalCode;

    @JsonProperty("ShippingAddressCity")
    String shippingAddressCity;

    @JsonProperty("ShippingAddressStreet")
    String shippingAddressStreet;

    @JsonProperty("ShippingAddressAdditionalInformation")
    String shippingAddressAdditionalInformation;

}
