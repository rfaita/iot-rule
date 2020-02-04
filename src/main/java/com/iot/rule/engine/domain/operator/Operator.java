package com.iot.rule.engine.domain.operator;

public interface Operator<T> {

    Boolean apply(T fieldValue, T value);

}
