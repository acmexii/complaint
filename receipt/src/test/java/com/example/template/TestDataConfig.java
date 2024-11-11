package com.example.template;

import complaint.domain.Complainment;
import complaint.domain.ComplainmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestDataConfig {

    @Bean
    public CommandLineRunner initData(ComplainmentRepository repository) {
        return args -> {
            Complainment complainment = new Complainment();
            complainment.setId(1L);
            complainment.setComplainId("C1001");
            complainment.setUserId("U1001");
            complainment.setComplainDetail("[object Object]");
            repository.save(complainment);
        };
    }
}
