package se.test.trustlydepositmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"se.test.trustlydepositmanager"})
@EnableJpaRepositories(value = "se.test.trustlydepositmanager.repository")
public class TrustlyDepositManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrustlyDepositManagerApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
