package com.iot.rule.engine.domain.operator;

class EqualsStringOperator implements Operator<String> {
    @Override
    public Boolean apply(String fieldValue, String value) {
        if (fieldValue != null) {
            return fieldValue.equalsIgnoreCase(value);
        }
        return (value == null);
    }
}
