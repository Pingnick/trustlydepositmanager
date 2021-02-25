package se.test.trustlydepositmanager.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SignatureService {

    public static String generateNewUUID() {
        return UUID.randomUUID().toString();
    }
}
