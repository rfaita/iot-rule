package com.iot.rule.engine.application.observers;

import com.iot.rule.engine.domain.IngestionData;
import com.iot.rule.engine.domain.RuleObservable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogNotificationObserver implements RuleObservable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogNotificationObserver.class);

    public void apply(IngestionData data) {
        LOGGER.info("New notification: {}", data);
    }
}