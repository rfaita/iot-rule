package com.iot.rule.engine.application.repository.impl;

import com.iot.rule.engine.infra.LastReachedTimeRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

public class LastReachedTimeRepositoryImpl implements LastReachedTimeRepository {

    private final RedisTemplate<String, Long> redisTemplate;

    public LastReachedTimeRepositoryImpl(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<Long> findByRuleId(String ruleId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(ruleId));
    }

    @Override
    public void save(String ruleId, Long lastReachedTime) {
        this.redisTemplate.opsForValue().set(ruleId, lastReachedTime);
    }
}
