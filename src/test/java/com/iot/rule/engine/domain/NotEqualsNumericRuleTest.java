package com.iot.rule.engine.domain;

import com.iot.rule.engine.helper.IngestionDataHelper;
import com.iot.rule.engine.domain.operator.OperatorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class NotEqualsNumericRuleTest {

    private Rule rule;

    private RuleObservable ruleObservable;

    @BeforeEach
    public void setUp() {

        this.ruleObservable = mock(RuleObservable.class);

        Condition<BigDecimal> condition =
                Condition.<BigDecimal>builder()
                        .field("numericTest")
                        .operatorType(OperatorType.NUMERIC_NOT_EQUALS)
                        .value(new BigDecimal(2))
                        .build();

        rule = new Rule("x", Arrays.asList(condition));
        rule.addObserver(this.ruleObservable);

    }

    @Test
    public void apply_numeric_rule_lower_number() {
        rule.apply(IngestionDataHelper.createNumericIngestionData(1));
        verify(ruleObservable, times(1)).apply(any());
    }

    @Test
    public void apply_numeric_rule_equals_number() {
        rule.apply(IngestionDataHelper.createNumericIngestionData(2));
        verify(ruleObservable, times(0)).apply(any());
    }

    @Test
    public void apply_numeric_rule_greater_number() {
        rule.apply(IngestionDataHelper.createNumericIngestionData(3));
        verify(ruleObservable, times(1)).apply(any());
    }


    @Test
    public void apply_numeric_rule_null_value() {
        rule.apply(IngestionDataHelper.createNumericIngestionData((Integer) null));
        verify(ruleObservable, times(1)).apply(any());
    }


}
