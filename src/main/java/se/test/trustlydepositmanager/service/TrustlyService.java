package se.test.trustlydepositmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.test.trustlydepositmanager.model.Deposit;
import se.test.trustlydepositmanager.repository.DepositRepository;
import se.test.trustlydepositmanager.web.model.WebDepositRequest;

import java.net.URI;

@Service
public class TrustlyService {

    @Autowired
    DepositRepository depositRepository;

    @Autowired
    RestTemplate restTemplate;

    private URI trustlyApiURL = URI.create("https://test.trustly.com/1");
    private String username = "devcode";
    private String password = "change_this_gef4D37ypL";
    private String public_key = "entity=podcast";


    public Deposit makeDeposit(WebDepositRequest depositRequest) {

        // TODO: Everything
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        //restTemplate.getForEntity(trustlyApiURL, )


        return null;
    }

    public void makeRefund() {}

    public void makeWithDrawal() {}


}
