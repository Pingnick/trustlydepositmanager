package se.test.trustlydepositmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.DepositRequestData;
import se.test.trustlydepositmanager.model.Deposit;
import se.test.trustlydepositmanager.repository.DepositRepository;
import se.test.trustlydepositmanager.web.model.WebDepositRequest;

@Service
public class DepositService {

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    TrustlyService trustlyService;


    public Deposit makeDeposit(WebDepositRequest depositRequest, String corrolationUuid) {

        // TODO: 1. Validate deposit request

        // TODO: 2. Store request locally

        // TODO: 3. Send request to Trustly
        Deposit deposit = sendDepositTransactionToTrystly();

        // TODO: 4. Update local stored request status

        // TODO: 5. Return response

        return deposit;
    }


    public Deposit makeQuickDeposit() {
        return null;
    }



    private Deposit sendDepositTransactionToTrystly() {
        // TODO: Send request to Trustly


        return null;
    }

    private DepositRequestData createTrustlyDepositRequest(WebDepositRequest depositRequest) {
        return null;
    }

    private void validateDepositRequest() {
        // TODO: Validate or throw an DepositInvalidException
    }
}
