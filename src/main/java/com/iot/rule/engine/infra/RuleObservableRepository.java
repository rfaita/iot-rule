package com.iot.rule.engine.infra;

import com.iot.rule.engine.domain.RuleObservable;

import java.util.List;

public interface RuleObservableRepository {

    List<RuleObservable> findAllByRuleId(String ruleId);

}
