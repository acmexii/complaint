package com.example.template;

import complaint.domain.Fee;
import complaint.domain.FeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestDataConfig {

    @Bean
    public CommandLineRunner initData(FeeRepository repository) {
        return args -> {
            Fee fee = new Fee();
            fee.setId(1L);
            fee.setApplicationNumber("A001");
            fee.setCharge(100L);
            fee.setUserId("user001");
            repository.save(fee);
        };
    }
}
