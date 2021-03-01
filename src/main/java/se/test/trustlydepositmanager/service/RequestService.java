package se.test.trustlydepositmanager.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.test.trustlydepositmanager.exceptions.CustomerException;
import se.test.trustlydepositmanager.model.Customer;
import se.test.trustlydepositmanager.model.Transaction;
import se.test.trustlydepositmanager.repository.CustomerRepository;
import se.test.trustlydepositmanager.rest.trustly.requests.RequestParameters;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.Attributes;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.DepositRequest;
import se.test.trustlydepositmanager.rest.trustly.requests.deposit.DepositRequestData;
import se.test.trustlydepositmanager.repository.TransactionRepository;
import se.test.trustlydepositmanager.rest.trustly.responses.DepositResponse;
import se.test.trustlydepositmanager.service.security.SigningService;
import se.test.trustlydepositmanager.rest.web.PaymentRequest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestService {

    @Getter
    @Value("${tdm.api.trustly.url}")
    private String apiURL;

    @Getter
    @Value("${tdm.api.notifications.url}")
    private String notificationBaseURL;


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    SigningService signingService;


    public DepositResponse makeDeposit(PaymentRequest paymentRequest, String correlationUuid) {

         Optional<Customer> optionalCustomer = customerRepository.findByCustomerUuid(paymentRequest.getEndUserUuid());

        if (!optionalCustomer.isPresent()) {
            throw new CustomerException(correlationUuid, "Customer not existing");
        }

        // TODO: 2. Store request locally, set status to Created

        DepositRequest depositRequest = createDepositRequestAndSign(paymentRequest, optionalCustomer.get());

        DepositResponse depositResponse = sendDepositRequest(depositRequest, correlationUuid);

        // TODO: 4. Update local stored request status to Pending

        return depositResponse;
    }

    public Transaction makeWithdraw() { return null; }
    public Transaction makeRefund() { return null; }
    public Transaction makeQuickDeposit() { return null; }



    private DepositResponse sendDepositRequest(DepositRequest depositRequest, String correlationUuid) {
        // TODO: Send request to Trustly


        return null;
    }

    private DepositRequest createDepositRequestAndSign(PaymentRequest paymentRequest, Customer customer) {

        DepositRequest depositRequest = DepositRequest.builder()
                .params(RequestParameters.builder()
                        .uuid(UUID.randomUUID().toString())
                        .data(DepositRequestData.builder()
                                .userName(signingService.getUserName())
                                .password(signingService.getPassword())
                                .notificationURL(notificationBaseURL + "/credit")
                                .messageId(paymentRequest.getPaymentRequestUuid())
                                .endUserId(customer.getUserUuid())
                                .attributes(Attributes.builder()
                                        .firstName(customer.getFirstName())
                                        .lastName(customer.getLastName())
                                        .email(customer.getEmail())
                                        .mobilePhone(customer.getMobilePhone())
                                        .amount(new BigDecimal(paymentRequest.getAmount()))
                                        .currency(customer.getCurrency())
                                        .country(customer.getCountry())
                                        .locale(customer.getLocale())
                                        .build())
                                .build())
                        .build())
                .build();

        signingService.signDepositRequest(depositRequest);


        return depositRequest;
    }


}
