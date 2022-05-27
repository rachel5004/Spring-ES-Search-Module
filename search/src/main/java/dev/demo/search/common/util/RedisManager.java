package dev.demo.search.common.util;

import dev.demo.search.common.exception.NotFoundException;
import dev.demo.search.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class RedisManager {

    private final StringRedisTemplate redisTemplate;

    public String getValue(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String value = valueOperations.get(key);
        if (value == null) {
            throw new NotFoundException(ResponseCode.REDIS_KEY_NOT_FOUND);
        }
        return value;
    }

    public void setValue(String key, String value, long timeout) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, Duration.ofMillis(timeout));
    }

    public void deleteValue(String key) {
        if (Boolean.FALSE.equals(redisTemplate.delete(key))) {
            throw new NotFoundException(ResponseCode.REDIS_KEY_NOT_FOUND);
        }
    }
}