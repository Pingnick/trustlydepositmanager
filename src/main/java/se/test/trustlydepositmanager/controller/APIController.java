package se.test.trustlydepositmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.test.trustlydepositmanager.model.Transaction;
import se.test.trustlydepositmanager.rest.trustly.responses.DepositResponse;
import se.test.trustlydepositmanager.rest.web.PaymentRequest;
import se.test.trustlydepositmanager.service.RequestService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class APIController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RequestService requestService;

    @RequestMapping(value = "/deposit",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<DepositResponse> createDepositRequest(@Valid @RequestBody PaymentRequest depositRequest) {

        LOGGER.info("Recieved a payment/deposit request from user:" + depositRequest);

        String correlationUuid = UUID.randomUUID().toString();

        DepositResponse depositResponse = requestService.makeDeposit(depositRequest, correlationUuid);

        return ResponseEntity.ok(depositResponse);
    }




}
