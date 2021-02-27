package se.test.trustlydepositmanager.rest.trustly.notifications.requests.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import se.test.trustlydepositmanager.config.ZonedDateTimeConfiguration;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.NotificationRequestData;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountNotificationRequestData extends NotificationRequestData {

    public static final DateTimeFormatter FORMATTER = ZonedDateTimeConfiguration.FORMATTER;
    public static final String DATE_STRING_PATTERN = "yyyy-MM-dd' 'HH:mm:ss.SSSSSSX";

    @JsonProperty("accountid")
    String accountId;

    @JsonProperty("verified")
    boolean isVerified;

    @JsonProperty("attributes")
    AccountAttributes accountAttributes;

}
