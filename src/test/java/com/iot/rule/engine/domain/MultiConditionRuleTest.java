package com.iot.rule.engine.domain;

import com.iot.rule.engine.domain.operator.OperatorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.iot.rule.engine.helper.IngestionDataHelper.createDefaultIngestionData;
import static org.mockito.Mockito.*;

public class MultiConditionRuleTest {

    private Rule rule;

    private RuleObservable ruleObservable;

    @BeforeEach
    public void setUp() {

        this.ruleObservable = mock(RuleObservable.class);

        Condition<BigDecimal> numericCondition =
                Condition.<BigDecimal>builder()
                        .field("numericTest")
                        .operatorType(OperatorType.NUMERIC_EQUALS)
                        .value(new BigDecimal(2))
                        .build();

        Condition<String> stringCondition =
                Condition.<String>builder()
                        .field("stringTest")
                        .operatorType(OperatorType.STRING_EQUALS)
                        .value("test")
                        .build();

        rule = new Rule("x", Arrays.asList(stringCondition, numericCondition));
        rule.addObserver(this.ruleObservable);

    }

    @Test
    public void multi_condition_equals_values() {
        rule.apply(createDefaultIngestionData(2, "test"));
        verify(ruleObservable, times(1)).apply(any());
    }

    @Test
    public void multi_condition_equal_numeric_not_equal_string() {
        rule.apply(createDefaultIngestionData(2, "test2"));
        verify(ruleObservable, times(0)).apply(any());
    }

    @Test
    public void multi_condition_not_equal_numeric_equal_string() {
        rule.apply(createDefaultIngestionData(3, "test"));
        verify(ruleObservable, times(0)).apply(any());
    }

    @Test
    public void multi_condition_not_equal_numeric_not_equal_string() {
        rule.apply(createDefaultIngestionData(3, "test2"));
        verify(ruleObservable, times(0)).apply(any());
    }

    @Test
    public void multi_condition_null_numeric_equal_string() {
        rule.apply(createDefaultIngestionData(null, "test"));
        verify(ruleObservable, times(0)).apply(any());
    }

    @Test
    public void multi_condition_equal_numeric_null_string() {
        rule.apply(createDefaultIngestionData(2, null));
        verify(ruleObservable, times(0)).apply(any());
    }

    @Test
    public void multi_condition_null_numeric_null_string() {
        rule.apply(createDefaultIngestionData(null, null));
        verify(ruleObservable, times(0)).apply(any());
    }

}
