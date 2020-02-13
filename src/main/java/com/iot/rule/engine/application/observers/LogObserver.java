package com.iot.rule.engine.application.observers;

import com.iot.rule.engine.domain.IngestionData;
import com.iot.rule.engine.domain.RuleObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogObserver implements RuleObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogObserver.class);

    public void apply(IngestionData data) {
        LOGGER.info("New condition reached to: {}", data);
    }
}