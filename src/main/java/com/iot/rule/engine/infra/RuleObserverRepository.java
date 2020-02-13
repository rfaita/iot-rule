package com.iot.rule.engine.infra;

import com.iot.rule.engine.domain.RuleObserver;

import java.util.List;

public interface RuleObserverRepository {

    List<RuleObserver> findAllByRuleId(String ruleId);

}
