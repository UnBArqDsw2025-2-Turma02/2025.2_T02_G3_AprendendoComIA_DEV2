package com.ailinguo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AILinguoApplication {
    public static void main(String[] args) {
        SpringApplication.run(AILinguoApplication.class, args);
    }
}

