package se.test.trustlydepositmanager.rest.trustly.notifications.requests.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.format.annotation.DateTimeFormat;
import se.test.trustlydepositmanager.rest.tools.ZonedDateTimeJsonDeserializer;
import se.test.trustlydepositmanager.rest.tools.ZonedDateTimeJsonSerializer;
import se.test.trustlydepositmanager.rest.trustly.notifications.requests.NotificationRequestData;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DebitNotificationRequestData extends NotificationRequestData {
    @JsonProperty("amount")
    BigDecimal amount;

    @JsonProperty("currency")
    @Size(min = 3, max = 3)
    String currency;

    @JsonProperty("enduserid")
    String endUserId;

    @JsonProperty("timestamp")
    @JsonDeserialize(using = ZonedDateTimeJsonDeserializer.class)
    @JsonSerialize(using = ZonedDateTimeJsonSerializer.class)
    @DateTimeFormat(pattern = DATE_STRING_PATTERN)
    ZonedDateTime timestamp;


}
