package com.iot.rule.engine.infra;

import com.iot.rule.engine.domain.Rule;

import java.util.List;

public interface RuleRepository {

    List<Rule> findAllByCustomerId(String customerId);

}
