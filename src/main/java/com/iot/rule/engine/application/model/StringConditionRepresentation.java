package com.iot.rule.engine.application.model;

import com.iot.rule.engine.domain.Condition;
import com.iot.rule.engine.domain.operator.OperatorType;

import java.math.BigDecimal;

public class StringConditionRepresentation implements ConditionRepresentation<String> {

    private OperatorType operatorType;
    private String field;
    private String value;

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(OperatorType operatorType) {
        this.operatorType = operatorType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Condition<String> toCondition() {
        return Condition.<String>builder()
                .operatorType(this.operatorType)
                .field(this.field)
                .value(this.value)
                .build();

    }
}
