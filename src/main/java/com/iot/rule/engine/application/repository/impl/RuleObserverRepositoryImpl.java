package com.iot.rule.engine.application.repository.impl;

import com.iot.rule.engine.application.observers.LogObserver;
import com.iot.rule.engine.application.observers.NotificationObserver;
import com.iot.rule.engine.application.repository.RuleNotificationRepository;
import com.iot.rule.engine.domain.RuleObserver;
import com.iot.rule.engine.infra.RuleObserverRepository;

import java.util.Arrays;
import java.util.List;

public class RuleObserverRepositoryImpl implements RuleObserverRepository {

    private final RuleNotificationRepository ruleNotificationRepository;

    public RuleObserverRepositoryImpl(RuleNotificationRepository ruleNotificationRepository) {
        this.ruleNotificationRepository = ruleNotificationRepository;
    }

    @Override
    public List<RuleObserver> findAllByRuleId(String ruleId) {
        return Arrays.asList(
                new NotificationObserver(this.ruleNotificationRepository.findAllByRuleId(ruleId)),
                new LogObserver()
        );
    }
}
