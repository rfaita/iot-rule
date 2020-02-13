package com.iot.rule.engine.domain;

import com.iot.rule.engine.domain.operator.OperatorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.iot.rule.engine.helper.IngestionDataHelper.createNumericIngestionData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RuleWithBounceTimeTest {

    private Rule rule;

    private RuleObserver ruleObserver;
    private RuleBounceTimeObserver ruleBounceTimeObserver;

    @BeforeEach
    public void setUp() {

        this.ruleObserver = mock(RuleObserver.class);
        this.ruleBounceTimeObserver = mock(RuleBounceTimeObserver.class);

        Condition<BigDecimal> condition =
                Condition.<BigDecimal>builder()
                        .field("numericTest")
                        .operatorType(OperatorType.NUMERIC_NOT_EQUALS)
                        .value(new BigDecimal(1))
                        .build();

        rule = new Rule("x", Arrays.asList(condition), Second.of(1));
        rule.addObserver(this.ruleObserver);
        rule.addBounceObserver(this.ruleBounceTimeObserver);

    }

    @Test
    public void apply_bounce_time_with_success() {
        rule.apply(createNumericIngestionData(2));
        rule.apply(createNumericIngestionData(2));
        rule.apply(createNumericIngestionData(2));
        rule.apply(createNumericIngestionData(2));
        verify(ruleObserver, times(1)).apply(any());
        verify(ruleBounceTimeObserver, times(1)).apply(any());
    }

    @Test
    public void apply_bounce_time_with_waiting_1010_millisecs() throws Exception {
        rule.apply(createNumericIngestionData(2));
        Thread.sleep(1010);
        rule.apply(createNumericIngestionData(2));
        verify(ruleObserver, times(2)).apply(any());
        verify(ruleBounceTimeObserver, times(2)).apply(any());
    }

    @Test
    public void apply_bounce_time_with_waiting_990_millisecs() throws Exception {
        rule.apply(createNumericIngestionData(2));
        Thread.sleep(990);
        rule.apply(createNumericIngestionData(2));
        verify(ruleObserver, times(1)).apply(any());
        verify(ruleBounceTimeObserver, times(1)).apply(any());
    }

}
