package com.iot.rule.engine.domain;

import com.iot.rule.engine.domain.operator.OperatorType;
import com.iot.rule.engine.helper.IngestionDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class NotEqualsStringRuleTest {

    private Rule rule;

    private RuleObserver ruleObserver;

    @BeforeEach
    public void setUp() {

        this.ruleObserver = mock(RuleObserver.class);

        Condition<String> condition =
                Condition.<String>builder()
                        .field("stringTest")
                        .operatorType(OperatorType.STRING_NOT_EQUALS)
                        .value("test")
                        .build();

        rule = new Rule("x", Arrays.asList(condition));
        rule.addObserver(this.ruleObserver);

    }

    @Test
    public void apply_string_rule_equal_value() {
        rule.apply(IngestionDataHelper.createStringIngestionData("test"));
        verify(ruleObserver, times(0)).apply(any());
    }

    @Test
    public void apply_string_rule_not_equal_value() {
        rule.apply(IngestionDataHelper.createStringIngestionData("test2"));
        verify(ruleObserver, times(1)).apply(any());
    }

    @Test
    public void apply_string_rule_null_value() {
        rule.apply(IngestionDataHelper.createStringIngestionData(null));
        verify(ruleObserver, times(1)).apply(any());
    }


}
