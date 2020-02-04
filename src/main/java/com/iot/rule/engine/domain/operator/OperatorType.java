package com.iot.rule.engine.domain.operator;

public enum OperatorType {

    STRING_EQUALS(new EqualsStringOperator()),
    NUMERIC_EQUALS(new EqualsNumericOperator()),
    STRING_NOT_EQUALS(new NotEqualsStringOperator()),
    NUMERIC_NOT_EQUALS(new NotEqualsNumericOperator()),
    NUMERIC_GTE(new GreaterEqualsNumericOperator()),
    NUMERIC_GT(new GreaterNumericOperator()),
    NUMERIC_LTE(new LessEqualsNumericOperator()),
    NUMERIC_LT(new LessNumericOperator());

    private final Operator<?> operator;

    public Operator<?> getOperator() {
        return operator;
    }

    OperatorType(Operator<?> operator) {
        this.operator = operator;
    }
}
