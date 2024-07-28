package org.user_api_service.app.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final JsonMapper jsonMapper;

    public void saveObject(String key, Object value) throws JsonProcessingException {
        redisTemplate.opsForValue().set(key, jsonMapper.writeValueAsString(value));
    }

    public <T> T getObject(String key, Class<T> c) throws JsonProcessingException {
        return jsonMapper.readValue((String) redisTemplate.opsForValue().get(key), c);
    }

    public void saveToList(String key, Object value) throws JsonProcessingException {
        redisTemplate.opsForList().rightPush(key, jsonMapper.writeValueAsString(value));
    }

    public <T> List<T> getFromList(String key, Class<T> c) {
        return redisTemplate.opsForList().range(key, 0, -1).stream().map(e -> {

            try {
                return jsonMapper.readValue((String) e, c);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }).toList();
    }

    public void removeFromList(String key, String value) throws JsonProcessingException {
        redisTemplate.opsForList().remove(key, 0, jsonMapper.writeValueAsString(value));
    }
}
