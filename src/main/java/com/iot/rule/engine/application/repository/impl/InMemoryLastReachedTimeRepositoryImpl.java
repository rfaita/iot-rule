package com.iot.rule.engine.application.repository.impl;

import com.iot.rule.engine.infra.LastReachedTimeRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryLastReachedTimeRepositoryImpl implements LastReachedTimeRepository {

    private final Map<String, Long> database = new HashMap<>();

    @Override
    public Optional<Long> findByRuleId(String ruleId) {
        return Optional.ofNullable(database.getOrDefault(ruleId, null));
    }

    @Override
    public void save(String ruleId, Long lastReachedTime) {
        this.database.put(ruleId, lastReachedTime);
    }
}
