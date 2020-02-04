package com.iot.rule.engine.application.consumer;

import com.iot.rule.engine.application.dto.SensorData;
import com.iot.rule.engine.infra.RuleEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
public class RuleEngineConsumer {

    private final static Logger LOGGER = LoggerFactory.getLogger(RuleEngineConsumer.class.getName());
    private final RuleEngineService ruleEngineService;

    public RuleEngineConsumer(RuleEngineService ruleEngineService) {
        this.ruleEngineService = ruleEngineService;
    }

    @StreamListener(Sink.INPUT)
    public void handle(SensorData sensorData) {
        LOGGER.info("Sensor Data : {}", sensorData);

        this.ruleEngineService.applyRules(sensorData.toIngestionData());
    }

}
