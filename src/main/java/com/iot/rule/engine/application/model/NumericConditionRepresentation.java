package com.iot.rule.engine.application.model;

import com.iot.rule.engine.domain.Condition;
import com.iot.rule.engine.domain.operator.OperatorType;

import java.math.BigDecimal;

public class NumericConditionRepresentation implements ConditionRepresentation<BigDecimal> {

    private OperatorType operatorType;
    private String field;
    private BigDecimal value;

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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Condition<BigDecimal> toCondition() {
        return Condition.<BigDecimal>builder()
                .operatorType(this.operatorType)
                .field(this.field)
                .value(this.value)
                .build();

    }
}
