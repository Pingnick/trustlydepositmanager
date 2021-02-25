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
import se.test.trustlydepositmanager.web.model.WebDepositRequest;
import se.test.trustlydepositmanager.model.Deposit;
import se.test.trustlydepositmanager.service.DepositService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/trustly/deposit")
public class DepositController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DepositService depositService;

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Deposit> createDepositRequest(@Valid @RequestBody WebDepositRequest depositRequest) {

        LOGGER.info("Recieved a deposit request from user:" + depositRequest);

        String corrolationUuid = UUID.randomUUID().toString();

        Deposit depositResponse = depositService.makeDeposit(depositRequest, corrolationUuid);

        return ResponseEntity.ok(depositResponse);
    }




}
