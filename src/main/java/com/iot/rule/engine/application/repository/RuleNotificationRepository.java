package com.iot.rule.engine.application.repository;

import com.iot.rule.engine.application.model.RuleNotification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RuleNotificationRepository extends MongoRepository<RuleNotification, String> {

    List<RuleNotification> findAllByRuleId(String ruleId);
}
