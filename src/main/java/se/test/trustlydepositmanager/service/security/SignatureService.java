package se.test.trustlydepositmanager.service.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class SignatureService {


    @Value("${trustlydepositmanager.security.public_key_path}")
    private String publicKeyPath;

    @Value("${trustlydepositmanager.security.public_key_password}")
    private String publicKeyPassword;











    public static String generateNewUUID() {
        return UUID.randomUUID().toString();
    }
}
