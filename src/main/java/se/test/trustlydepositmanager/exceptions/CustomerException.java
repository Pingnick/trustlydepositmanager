package se.test.trustlydepositmanager.exceptions;

import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException{

    String correlationUuid;
    String additionalInformation;

    public CustomerException(final String correlationUuid, final String additionalInformation, final Throwable exception) {
        super(exception);

        this.correlationUuid = correlationUuid;
        this.additionalInformation = additionalInformation;
    }


    public CustomerException(final String correlationUuid, final String additionalInformation) {
        this.correlationUuid = correlationUuid;
        this.additionalInformation = additionalInformation;
    }
}
