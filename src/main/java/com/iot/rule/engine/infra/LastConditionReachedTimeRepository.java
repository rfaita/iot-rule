package com.iot.rule.engine.infra;

import java.util.Optional;

public interface LastConditionReachedTimeRepository {

    Optional<Long> findByRuleId(String ruleId);

    void save(String ruleId, Long lastConditionReachedTime);

}
