package com.example.template.adapters.out.cache;

import com.example.template.domain.Greeting;
import com.example.template.domain.ports.out.GreetingCache;
import java.time.Duration;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisGreetingCache implements GreetingCache {

    private static final String KEY_PREFIX = "greeting:";
    private static final Duration TTL = Duration.ofMinutes(30);

    private final RedisTemplate<String, Greeting> redisTemplate;

    public RedisGreetingCache(RedisTemplate<String, Greeting> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<Greeting> get(UUID id) {
        var value = redisTemplate.opsForValue().get(keyFor(id));
        return Optional.ofNullable(value);
    }

    @Override
    public void put(Greeting greeting) {
        redisTemplate.opsForValue().set(keyFor(greeting.id()), greeting, TTL);
    }

    @Override
    public void evict(UUID id) {
        redisTemplate.delete(keyFor(id));
    }

    @Override
    public void evictAll() {
        Set<String> keys = redisTemplate.keys(KEY_PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    private String keyFor(UUID id) {
        return KEY_PREFIX + id.toString();
    }
}
