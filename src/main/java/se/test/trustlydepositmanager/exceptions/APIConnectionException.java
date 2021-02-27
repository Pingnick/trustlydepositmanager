package se.test.trustlydepositmanager.exceptions;

import lombok.Getter;

@Getter
public class APIConnectionException extends APIException{

    private static final String ADDITIONAL_INFORMATION = "API Connection Error";

    public APIConnectionException(final String correlationUuid, final String additionalInformation,
                                  final Throwable exception) {
        super(correlationUuid, additionalInformation, exception);
    }

    public APIConnectionException(final String additionalInformation, final Throwable exception) {
        super("N/A", additionalInformation, exception);
    }

    public APIConnectionException(final Throwable exception) {
        super("N/A", ADDITIONAL_INFORMATION, exception);
    }
}
