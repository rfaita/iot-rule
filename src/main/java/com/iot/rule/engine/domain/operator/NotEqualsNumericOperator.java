package com.iot.rule.engine.domain.operator;

import java.math.BigDecimal;

class NotEqualsNumericOperator extends EqualsNumericOperator {

    @Override
    public Boolean apply(BigDecimal fieldValue, BigDecimal value) {
        return !super.apply(fieldValue, value);
    }
}
