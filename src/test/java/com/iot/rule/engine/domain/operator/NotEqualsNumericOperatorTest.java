package com.iot.rule.engine.domain.operator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;

public class NotEqualsNumericOperatorTest {

    private Operator<BigDecimal> notEqualsBigDecimalOperator;

    @BeforeEach
    public void setUp() {
        notEqualsBigDecimalOperator = new NotEqualsNumericOperator();
    }

    @Test
    public void not_equals_with_success() {

        BigDecimal field = new BigDecimal(1);
        BigDecimal value = new BigDecimal(2);

        Assert.isTrue(notEqualsBigDecimalOperator.apply(field, value), "This test must return a true.");
    }

    @Test
    public void not_equals_with_fail() {

        BigDecimal field = new BigDecimal(1);
        BigDecimal value = new BigDecimal(1);

        Assert.isTrue(!notEqualsBigDecimalOperator.apply(field, value), "This test must return a false.");
    }

    @Test
    public void not_equals_with_null_first_value() {

        BigDecimal field = null;
        BigDecimal value = new BigDecimal(1);

        Assert.isTrue(notEqualsBigDecimalOperator.apply(field, value), "This test must return a true.");
    }

    @Test
    public void not_equals_with_null_second_value() {

        BigDecimal field = new BigDecimal(1);
        BigDecimal value = null;

        Assert.isTrue(notEqualsBigDecimalOperator.apply(field, value), "This test must return a true.");
    }

    @Test
    public void not_equals_with_nulls_values() {

        BigDecimal field = null;
        BigDecimal value = null;

        Assert.isTrue(!notEqualsBigDecimalOperator.apply(field, value), "This test must return a false.");
    }
}
