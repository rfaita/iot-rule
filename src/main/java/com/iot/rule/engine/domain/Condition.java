package com.iot.rule.engine.domain;

import com.iot.rule.engine.domain.operator.Operator;
import com.iot.rule.engine.domain.operator.OperatorType;

import java.math.BigDecimal;

import static com.iot.rule.engine.domain.helper.ConverterHelper.convertToBigDecimal;

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
        Object fieldValue = data.getField(this.field);
        if (fieldValue instanceof Number) {
            return operator.apply((T) convertToBigDecimal(fieldValue), value);
        }
        return operator.apply((T) fieldValue, value);
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
