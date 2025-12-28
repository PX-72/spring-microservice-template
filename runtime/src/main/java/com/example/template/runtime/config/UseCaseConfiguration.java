package com.example.template.runtime.config;

import com.example.template.domain.GreetingUseCase;
import com.example.template.domain.ports.out.GreetingStore;
import com.example.template.domain.services.GreetingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {
    @Bean
    public GreetingUseCase greetingUseCase(GreetingStore greetingStore) {
        return new GreetingService(greetingStore);
    }
}
