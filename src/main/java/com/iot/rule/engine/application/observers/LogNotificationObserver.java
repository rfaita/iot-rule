package com.iot.rule.engine.application.observers;

import com.iot.rule.engine.domain.IngestionData;
import com.iot.rule.engine.domain.RuleObservable;

public class LogNotificationObserver implements RuleObservable {
    public void apply(IngestionData data) {
        System.out.println(data);
    }
}