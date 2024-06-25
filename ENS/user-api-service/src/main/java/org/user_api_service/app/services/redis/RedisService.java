package org.user_api_service.app.services.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.user_api_service.app.models.responeModels.User;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final JsonMapper jsonMapper;

    public void save(String key, Object value) throws JsonProcessingException {
        redisTemplate.opsForValue().set(key, jsonMapper.writeValueAsString(value));
    }

    public User getCurrentUser() throws JsonProcessingException {
        return jsonMapper.readValue((String) redisTemplate.opsForValue().get("current"), User.class);
    }
}
