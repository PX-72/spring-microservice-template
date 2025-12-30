package com.example.template.runtime.config;

import com.example.template.domain.ports.out.GreetingStore;
import com.example.template.domain.services.GreetingService;
import com.example.template.domain.services.GreetingServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {
    @Bean
    public GreetingService greetingService(GreetingStore greetingStore) {
        return new GreetingServiceImpl(greetingStore);
    }
}
