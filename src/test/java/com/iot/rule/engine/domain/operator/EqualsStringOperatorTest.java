package com.iot.rule.engine.domain.operator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class EqualsStringOperatorTest {

    private Operator<String> equalsStringOperator;

    @BeforeEach
    public void setUp() {
        equalsStringOperator = new EqualsStringOperator();
    }

    @Test
    public void equals_with_success() {

        String field = "teste1";
        String value = "teste1";

        Assert.isTrue(equalsStringOperator.apply(field, value), "This test must return a true.");
    }

    @Test
    public void equals_with_fail() {

        String field = "teste1";
        String value = "teste2";

        Assert.isTrue(!equalsStringOperator.apply(field, value), "This test must return a false.");
    }

    @Test
    public void equals_with_null_first_value() {

        String field = null;
        String value = "teste1";

        Assert.isTrue(!equalsStringOperator.apply(field, value), "This test must return a false.");
    }

    @Test
    public void equals_with_null_second_value() {

        String field = "teste1";
        String value = null;

        Assert.isTrue(!equalsStringOperator.apply(field, value), "This test must return a false.");
    }

    @Test
    public void equals_with_nulls_values() {

        String field = null;
        String value = null;

        Assert.isTrue(equalsStringOperator.apply(field, value), "This test must return a true.");
    }
}
