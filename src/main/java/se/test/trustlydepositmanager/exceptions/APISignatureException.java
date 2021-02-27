package se.test.trustlydepositmanager.exceptions;

import lombok.Getter;

@Getter
public class APISignatureException extends APIException{

    private static final String ADDITIONAL_INFORMATION = "API Signature Error";

    public APISignatureException(final String correlationUuid, final String additionalInformation,
                                 final Throwable exception) {
        super(correlationUuid, additionalInformation,  exception);
    }

    public APISignatureException(final String additionalInformation, final Throwable exception) {
        super("N/A", additionalInformation, exception);
    }

    public APISignatureException(final Throwable exception) {
        super("N/A", ADDITIONAL_INFORMATION, exception);
    }
}
