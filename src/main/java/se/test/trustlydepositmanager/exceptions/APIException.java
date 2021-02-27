package se.test.trustlydepositmanager.exceptions;

import lombok.Getter;

@Getter
public class APIException extends RuntimeException{

    String correlationUuid;
    String additionalInformation;

    public APIException(final String correlationUuid, final String additionalInformation, final Throwable exception) {
        super(exception);

        this.correlationUuid = correlationUuid;
        this.additionalInformation = additionalInformation;
    }

    /*
    public APIException(final String correlationUuid, final Throwable exception) {
        super(exception);

        this.correlationUuid = correlationUuid;
    }*/
}
