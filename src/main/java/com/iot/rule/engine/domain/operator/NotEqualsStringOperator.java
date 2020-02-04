package com.iot.rule.engine.domain.operator;

class NotEqualsStringOperator extends EqualsStringOperator {

    @Override
    public Boolean apply(String fieldValue, String value) {
        return !super.apply(fieldValue, value);
    }
}
