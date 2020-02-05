package com.iot.rule.engine.infra;

import com.iot.rule.engine.domain.Rule;

import java.util.List;

public interface RuleRepository {

    List<Rule> findAllByCustomerIdAndDeviceId(String customerId, String deviceId);

}
