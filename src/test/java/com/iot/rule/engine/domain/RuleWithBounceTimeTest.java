package com.iot.rule.engine.domain;

import com.iot.rule.engine.domain.operator.OperatorType;
import com.iot.rule.engine.helper.IngestionDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.iot.rule.engine.helper.IngestionDataHelper.createNumericIngestionData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RuleWithBounceTimeTest {

    private Rule rule;

    private RuleObservable ruleObservable;
    private RuleBounceTimeObservable ruleBounceTimeObservable;

    @BeforeEach
    public void setUp() {

        this.ruleObservable = mock(RuleObservable.class);
        this.ruleBounceTimeObservable = mock(RuleBounceTimeObservable.class);

        Condition<BigDecimal> condition =
                Condition.<BigDecimal>builder()
                        .field("numericTest")
                        .operatorType(OperatorType.NUMERIC_NOT_EQUALS)
                        .value(new BigDecimal(1))
                        .build();

        rule = new Rule("x", Arrays.asList(condition), Second.of(1));
        rule.addObserver(this.ruleObservable);
        rule.addBounceObserver(this.ruleBounceTimeObservable);

    }

    @Test
    public void apply_bounce_time_with_success() {
        rule.apply(createNumericIngestionData(2));
        rule.apply(createNumericIngestionData(2));
        rule.apply(createNumericIngestionData(2));
        rule.apply(createNumericIngestionData(2));
        verify(ruleObservable, times(1)).apply(any());
        verify(ruleBounceTimeObservable, times(1)).apply(any());
    }

    @Test
    public void apply_bounce_time_with_waiting_1_secs() throws Exception {
        rule.apply(createNumericIngestionData(2));
        Thread.sleep(1010);
        rule.apply(createNumericIngestionData(2));
        verify(ruleObservable, times(2)).apply(any());
        verify(ruleBounceTimeObservable, times(2)).apply(any());
    }

}
