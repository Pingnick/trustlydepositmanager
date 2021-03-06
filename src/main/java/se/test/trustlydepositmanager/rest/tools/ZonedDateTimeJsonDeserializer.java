package se.test.trustlydepositmanager.rest.tools;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

import static se.test.trustlydepositmanager.config.ZonedDateTimeConfiguration.FORMATTER;

public class ZonedDateTimeJsonDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        ZonedDateTime localDate = ZonedDateTime.parse(jsonParser.getText(), FORMATTER);

        return localDate;
    }
}