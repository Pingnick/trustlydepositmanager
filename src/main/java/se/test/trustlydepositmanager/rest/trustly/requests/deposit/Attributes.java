package se.test.trustlydepositmanager.rest.trustly.requests.deposit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import se.test.trustlydepositmanager.model.RecipientInformation;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
/*
@JsonPropertyOrder({"amount", "chargeAccountId", "country", "currency", "email", "externalReference", "failURL",
        "firstName", "ip", "lastName", "locale", "mobilePhone", "nationalIdentificationNumber", "quickDeposit",
        "recipientInformation", "requestDirectDebitMandate", "shippingAddress", "shippingAddressCity",
        "shippingAddressCountry", "shippingAdressLine1", "shippingAdressLine2", "shippingAddressPostalCode",
        "shopperStatement", "successURL", "suggestedMaxAmount", "suggestedMinAmount", "templateURL", "urlTarget",
        "", "", "", ""})
*/
public class Attributes {

    @JsonProperty("Currency")
    @Size(min = 3, max = 3)
    @NonNull
    String currency;    // char(3),

    @JsonProperty("Firstname")
    @NonNull
    String firstName;

    @JsonProperty("Lastname")
    @NonNull
    String lastName;

    @JsonProperty("Country")
    @Size(min = 2, max = 2)
    @NonNull
    String country;     // char(2) Ex. SE

    @JsonProperty("Locale")
    @Builder.Default
    @NonNull
    String locale = "se_SE";      // format [language[_territory]]. Ex. en_US

    @JsonProperty("Email")
    @NonNull
    String email;       // Required only when send attribute: RequestDirectDebitMandate, QuickDeposit, ChargeAccountID.

    @JsonProperty("Amount")
    BigDecimal amount;      // .- 2 decimal separator

    @JsonProperty("SuggestedMinAmount")
    BigDecimal suggestedMinAmount;

    @JsonProperty("SuggestedMaxAmount")
    BigDecimal suggestedMaxAmount;

    @JsonProperty("IP")
    String ip;

    @JsonProperty("SuccessURL")
    String successURL;

    @JsonProperty("FailURL")
    String failURL;

    @JsonProperty("TemplateURL")
    String templateURL;

    @JsonProperty("URLTarget")
    String urlTarget;

    @JsonProperty("MobilePhone")
    String mobilePhone;

    @JsonProperty("NationalIdentificationNumber")
    String nationalIdentificationNumber;

    @JsonProperty("UnchangeableNationalIdentificationNumber")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    boolean unchangeableNationalIdentificationNumber;

    @JsonProperty("ShopperStatement")
    String shopperStatement;

    @JsonProperty("ShippingAddressCountry")
    String shippingAddressCountry;  // char(2)

    @JsonProperty("ShippingAddressPostalCode")
    String shippingAddressPostalCode;

    @JsonProperty("ShippingAddressCity")
    String shippingAddressCity;

    @JsonProperty("ShippingAddressLine1")
    String shippingAdressLine1;

    @JsonProperty("ShippingAddressLine2")
    String shippingAdressLine2;

    @JsonProperty("ShippingAddress")
    String shippingAddress;

    @JsonProperty("RequestDirectDebitMandate")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    boolean requestDirectDebitMandate;

    @JsonProperty("ChargeAccountID")
    String chargeAccountId;

    @JsonProperty("QuickDeposit")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    boolean quickDeposit;

    @JsonProperty("ExternalReference")
    String externalReference;

    @JsonProperty("RecipientInformation")
    RecipientInformation recipientInformation;
}
