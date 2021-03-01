package se.test.trustlydepositmanager.rest.trustly.notifications.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.data.AccountNotificationRequestData;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.data.CancelNotificationRequestData;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.data.CreditNotificationRequestData;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.data.DebitNotificationRequestData;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.data.PendingNotificationRequestData;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AccountNotificationRequestData.class, name = "accountNotificationRequestData"),
        @JsonSubTypes.Type(value = CancelNotificationRequestData.class, name = "cancelNotificationRequestData"),
        @JsonSubTypes.Type(value = CreditNotificationRequestData.class, name = "creditNotificationRequestData"),
        @JsonSubTypes.Type(value = DebitNotificationRequestData.class, name = "debitNotificationRequestData"),
        @JsonSubTypes.Type(value = PendingNotificationRequestData.class, name = "pendingNotificationRequestData")
})
@JsonPropertyOrder(alphabetic = true)
public abstract class NotificationRequestData {

    public static final String DATE_STRING_PATTERN = "yyyy-MM-dd' 'HH:mm:ss.SSSSSSX";

    @JsonProperty("notificationid")
    String notificationId;

    @JsonProperty("messageid")
    String messageId;

    @JsonProperty("orderid")
    String orderId;

}
