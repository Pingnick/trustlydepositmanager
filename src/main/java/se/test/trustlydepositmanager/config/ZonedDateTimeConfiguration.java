package se.test.trustlydepositmanager.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import se.test.trustlydepositmanager.rest.trustly.ZonedDateTimeJsonSerializer;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ZonedDateTimeConfiguration {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss.SSSSSSX");

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //objectMapper.setTimeZone(TimeZone.getTimeZone(Locale.FRANCE.getDisplayName()));

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeJsonSerializer());
        objectMapper.registerModule(javaTimeModule);


        return objectMapper;
    }
}
