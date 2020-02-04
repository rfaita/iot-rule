package com.iot.rule.engine.application.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document
public class RuleNotification {

    @Id
    private String id;
    private String ruleId;
    private String customerId;
    private RuleNotificationType ruleNotificationType;
    private Map<String, Object> extraFields = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public RuleNotificationType getRuleNotificationType() {
        return ruleNotificationType;
    }

    public void setRuleNotificationType(RuleNotificationType ruleNotificationType) {
        this.ruleNotificationType = ruleNotificationType;
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


}
