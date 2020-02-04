package com.iot.rule.engine.domain.operator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;

public class GreaterEqualsNumericOperatorTest {

    private Operator<BigDecimal> equalsBigDecimalOperator;

    @BeforeEach
    public void setUp() {
        equalsBigDecimalOperator = new GreaterEqualsNumericOperator();
    }

    @Test
    public void greater_equals_with_success() {

        BigDecimal field = new BigDecimal(2);
        BigDecimal value = new BigDecimal(1);

        Assert.isTrue(equalsBigDecimalOperator.apply(field, value), "This test must return a true.");
    }

    @Test
    public void greater_equals_with_fail() {

        BigDecimal field = new BigDecimal(1);
        BigDecimal value = new BigDecimal(2);

        Assert.isTrue(!equalsBigDecimalOperator.apply(field, value), "This test must return a false.");
    }

    @Test
    public void greater_equals_with_equals_values() {

        BigDecimal field = new BigDecimal(1);
        BigDecimal value = new BigDecimal(1);

        Assert.isTrue(equalsBigDecimalOperator.apply(field, value), "This test must return a true.");
    }


    @Test
    public void greater_equals_with_null_first_value() {

        BigDecimal field = null;
        BigDecimal value = new BigDecimal(1);

        Assert.isTrue(!equalsBigDecimalOperator.apply(field, value), "This test must return a false.");
    }

    @Test
    public void greater_equals_with_null_second_value() {

        BigDecimal field = new BigDecimal(1);
        BigDecimal value = null;

        Assert.isTrue(!equalsBigDecimalOperator.apply(field, value), "This test must return a false.");
    }

    @Test
    public void greater_equals_with_nulls_values() {

        BigDecimal field = null;
        BigDecimal value = null;
        Assert.isTrue(!equalsBigDecimalOperator.apply(field, value), "This test must return a false.");
    }
}
