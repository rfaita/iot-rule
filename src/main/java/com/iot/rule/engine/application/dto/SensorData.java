package com.iot.rule.engine.application.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.iot.rule.engine.domain.IngestionData;

import java.util.HashMap;
import java.util.Map;

public class SensorData {

    private String id;
    private Long timestamp;
    private String tenantId;

    private Map<String, Object> extraFields = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @JsonAnyGetter
    public Map<String, Object> getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(Map<String, Object> extraFields) {
        this.extraFields = extraFields;
    }

    @JsonAnySetter
    public void setExtraFields(String key, Object value) {
        this.extraFields.put(key, value);
    }


    public IngestionData toIngestionData() {
        return new IngestionData(this.id, this.timestamp, this.tenantId, this.getExtraFields());
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", tenantId='" + tenantId + '\'' +
                ", extraFields=" + extraFields +
                '}';
    }
}
