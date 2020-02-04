package com.iot.rule.engine.domain.operator;

import java.math.BigDecimal;

class GreaterEqualsNumericOperator implements Operator<BigDecimal> {

    @Override
    public Boolean apply(BigDecimal fieldValue, BigDecimal value) {
        if (fieldValue != null && value != null) {
            return fieldValue.compareTo(value) >= 0;
        }
        return Boolean.FALSE;
    }
}
