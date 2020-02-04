package com.iot.rule.engine.domain.operator;

import java.math.BigDecimal;

class EqualsNumericOperator implements Operator<BigDecimal> {

    @Override
    public Boolean apply(BigDecimal fieldValue, BigDecimal value) {
        if (fieldValue != null) {
            return fieldValue.equals(value);
        }
        return (value == null);
    }
}
