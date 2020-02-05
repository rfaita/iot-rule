package com.iot.rule.engine.domain;

import java.util.Map;

public class IngestionData {

    private final String deviceId;
    private final Long timestamp;
    private final String customerId;
    private final Map<String, ?> data;

    public IngestionData(String deviceId, Long timestamp, String customerId, Map<String, ?> data) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.customerId = customerId;
        this.data = data;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Object getField(String field) {
        return this.data.getOrDefault(field, null);
    }

    @Override
    public String toString() {
        return "IngestionData{" +
                "deviceId='" + deviceId + '\'' +
                ", timestamp=" + timestamp +
                ", customerId='" + customerId + '\'' +
                ", data=" + data +
                '}';
    }
}
