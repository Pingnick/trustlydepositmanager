package se.test.trustlydepositmanager.exceptions;

import lombok.Getter;

@Getter
public class APIDataException extends APIException{

    private static final String ADDITIONAL_INFORMATION = "API Data Error";

    public APIDataException(final String correlationUuid, final String additionalInformation, final Throwable exception) {
        super(correlationUuid, additionalInformation, exception);
    }

    public APIDataException(final String additionalInformation, final Throwable exception) {
        super("N/A", additionalInformation, exception);
    }

    public APIDataException(final Throwable exception) {
        super("N/A", ADDITIONAL_INFORMATION, exception);
    }
}
