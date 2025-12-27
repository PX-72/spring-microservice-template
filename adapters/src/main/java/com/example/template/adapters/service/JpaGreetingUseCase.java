package com.example.template.adapters.service;

import com.example.template.adapters.persistence.GreetingEntity;
import com.example.template.adapters.persistence.GreetingRepository;
import com.example.template.domain.Greeting;
import com.example.template.domain.GreetingUseCase;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaGreetingUseCase implements GreetingUseCase {
  private final GreetingRepository greetingRepository;

  public JpaGreetingUseCase(GreetingRepository greetingRepository) {
    this.greetingRepository = greetingRepository;
  }

  @Override
  @Transactional
  public Greeting createGreeting(String name) {
    var id = UUID.randomUUID();
    var message = "Hello, " + name + "!";
    var entity = new GreetingEntity(id, message);

    this.greetingRepository.save(entity);

    return new Greeting(id, message);
  }
}
