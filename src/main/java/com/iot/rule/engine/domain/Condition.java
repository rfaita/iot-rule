package com.iot.rule.engine.domain;

import com.iot.rule.engine.domain.operator.Operator;
import com.iot.rule.engine.domain.operator.OperatorType;

public class Condition<T> {

    private final Operator operator;
    private final String field;
    private final T value;

    private Condition(Builder builder) {
        this.operator = builder.operatorType.getOperator();
        this.field = builder.field;
        this.value = (T) builder.value;
    }

    public Boolean apply(IngestionData data) {

        return operator.apply((T) data.getField(this.field), value);

    }

    public static <T> Builder<T> builder() {
        return new Builder<T>();
    }

    public static class Builder<T> {
        private OperatorType operatorType;
        private String field;
        private T value;

        public Builder<T> operatorType(OperatorType operatorType) {
            this.operatorType = operatorType;
            return this;
        }

        public Builder<T> field(String field) {
            this.field = field;
            return this;
        }

        public Builder<T> value(T value) {
            this.value = value;
            return this;
        }

        public Condition<T> build() {
            return new Condition(this);
        }
    }
}
