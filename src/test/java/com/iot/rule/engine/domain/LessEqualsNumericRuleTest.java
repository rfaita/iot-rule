package com.iot.rule.engine.domain;

import com.iot.rule.engine.domain.operator.OperatorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.iot.rule.engine.helper.IngestionDataHelper.createNumericIngestionData;
import static org.mockito.Mockito.*;

public class LessEqualsNumericRuleTest {

    private Rule rule;

    private RuleObserver ruleObserver;

    @BeforeEach
    public void setUp() {

        this.ruleObserver = mock(RuleObserver.class);

        Condition<BigDecimal> condition =
                Condition.<BigDecimal>builder()
                        .field("numericTest")
                        .operatorType(OperatorType.NUMERIC_LTE)
                        .value(new BigDecimal(2))
                        .build();

        rule = new Rule("x", Arrays.asList(condition));
        rule.addObserver(this.ruleObserver);

    }

    @Test
    public void apply_numeric_rule_lower_number() {
        rule.apply(createNumericIngestionData(1));
        verify(ruleObserver, times(1)).apply(any());
    }

    @Test
    public void apply_numeric_rule_equals_number() {
        rule.apply(createNumericIngestionData(2));
        verify(ruleObserver, times(1)).apply(any());
    }

    @Test
    public void apply_numeric_rule_greater_number() {
        rule.apply(createNumericIngestionData(3));
        verify(ruleObserver, times(0)).apply(any());
    }


    @Test
    public void apply_numeric_rule_null_value() {
        rule.apply(createNumericIngestionData((Integer) null));
        verify(ruleObserver, times(0)).apply(any());
    }


}
