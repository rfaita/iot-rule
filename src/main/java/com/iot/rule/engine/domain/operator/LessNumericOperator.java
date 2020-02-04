package com.iot.rule.engine.domain.operator;

import java.math.BigDecimal;

class LessNumericOperator implements Operator<BigDecimal> {

    @Override
    public Boolean apply(BigDecimal fieldValue, BigDecimal value) {
        if (fieldValue != null && value != null) {
            return fieldValue.compareTo(value) == -1;
        }
        return Boolean.FALSE;
    }
}
