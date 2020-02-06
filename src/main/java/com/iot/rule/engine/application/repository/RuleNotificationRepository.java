package com.iot.rule.engine.application.repository;

import com.iot.rule.engine.application.model.RuleNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleNotificationRepository extends MongoRepository<RuleNotification, String> {

    List<RuleNotification> findAllByRuleId(String ruleId);
}
