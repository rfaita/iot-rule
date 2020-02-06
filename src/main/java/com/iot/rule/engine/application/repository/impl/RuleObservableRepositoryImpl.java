package com.iot.rule.engine.application.repository.impl;

import com.iot.rule.engine.application.observers.LogObserver;
import com.iot.rule.engine.application.observers.NotificationObserver;
import com.iot.rule.engine.application.repository.RuleNotificationRepository;
import com.iot.rule.engine.domain.RuleObservable;
import com.iot.rule.engine.infra.RuleObservableRepository;

import java.util.Arrays;
import java.util.List;

public class RuleObservableRepositoryImpl implements RuleObservableRepository {

    private final RuleNotificationRepository ruleNotificationRepository;

    public RuleObservableRepositoryImpl(RuleNotificationRepository ruleNotificationRepository) {
        this.ruleNotificationRepository = ruleNotificationRepository;
    }

    @Override
    public List<RuleObservable> findAllByRuleId(String ruleId) {
        return Arrays.asList(
                new NotificationObserver(this.ruleNotificationRepository.findAllByRuleId(ruleId)),
                new LogObserver()
        );
    }
}
