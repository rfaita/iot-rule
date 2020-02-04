package com.iot.rule.engine.domain;

import java.util.Map;

public class IngestionData {

    private final String sensorId;
    private final Long timestamp;
    private final String customerId;
    private final Map<String, ?> data;

    public IngestionData(String sensorId, Long timestamp, String customerId, Map<String, ?> data) {
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.customerId = customerId;
        this.data = data;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Object getField(String field) {
        return this.data.getOrDefault(field, null);
    }
}
