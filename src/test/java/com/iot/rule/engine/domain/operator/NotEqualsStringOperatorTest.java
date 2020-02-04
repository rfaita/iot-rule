package com.iot.rule.engine.domain.operator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class NotEqualsStringOperatorTest {

    private Operator<String> notEqualsStringOperator;

    @BeforeEach
    public void setUp() {
        notEqualsStringOperator = new NotEqualsStringOperator();
    }

    @Test
    public void not_equals_with_success() {

        String field = "teste1";
        String value = "teste2";

        Assert.isTrue(notEqualsStringOperator.apply(field, value), "This test must return a true.");
    }

    @Test
    public void not_equals_with_fail() {

        String field = "teste1";
        String value = "teste1";

        Assert.isTrue(!notEqualsStringOperator.apply(field, value), "This test must return a false.");
    }

    @Test
    public void not_equals_with_null_first_value() {

        String field = null;
        String value = "teste1";

        Assert.isTrue(notEqualsStringOperator.apply(field, value), "This test must return a true.");
    }

    @Test
    public void not_equals_with_null_second_value() {

        String field = "teste1";
        String value = null;

        Assert.isTrue(notEqualsStringOperator.apply(field, value), "This test must return a true.");
    }

    @Test
    public void not_equals_with_nulls_values() {

        String field = null;
        String value = null;

        Assert.isTrue(!notEqualsStringOperator.apply(field, value), "This test must return a false.");
    }
}
