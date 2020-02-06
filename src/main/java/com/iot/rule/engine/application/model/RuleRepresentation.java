package com.iot.rule.engine.application.model;

import com.iot.rule.engine.domain.Condition;
import com.iot.rule.engine.domain.Rule;
import com.iot.rule.engine.domain.Second;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Document
public class RuleRepresentation {

    @Id
    private String id;
    private String deviceId;
    private String customerId;
    private List<ConditionRepresentation> conditions;
    private Second bounceTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<ConditionRepresentation> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionRepresentation> conditions) {
        this.conditions = conditions;
    }

    public Second getBounceTime() {
        return bounceTime;
    }

    public void setBounceTime(Second bounceTime) {
        this.bounceTime = bounceTime;
    }

    public Rule toRule() {
        List<Condition> conditions = this.conditions
                .stream()
                .map(ConditionRepresentation::toCondition)
                .collect(Collectors.toList());
        return new Rule(this.getId(), conditions, this.getBounceTime());
    }
}
