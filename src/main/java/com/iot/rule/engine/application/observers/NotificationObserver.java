package com.iot.rule.engine.application.observers;

import com.iot.rule.engine.application.model.RuleNotification;
import com.iot.rule.engine.domain.IngestionData;
import com.iot.rule.engine.domain.RuleObserver;

import java.util.List;

public class NotificationObserver implements RuleObserver {

    private final List<RuleNotification> ruleNotifications;

    public NotificationObserver(List<RuleNotification> ruleNotifications) {
        this.ruleNotifications = ruleNotifications;
    }

    @Override
    public void apply(IngestionData data) {
        System.out.println(data);
    }
}
