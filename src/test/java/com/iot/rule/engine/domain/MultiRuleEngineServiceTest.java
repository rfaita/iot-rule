package com.iot.rule.engine.domain;

import com.iot.rule.engine.infra.LastReachedTimeRepository;
import com.iot.rule.engine.infra.RuleObserverRepository;
import com.iot.rule.engine.infra.RuleEngineService;
import com.iot.rule.engine.infra.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.iot.rule.engine.helper.IngestionDataHelper.createNumericIngestionData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class MultiRuleEngineServiceTest {

    private RuleEngineService ruleEngineService;

    private RuleRepository ruleRepository;
    private RuleObserverRepository ruleObserverRepository;

    private Condition condition;
    private Condition conditionNotApplied;
    private RuleObserver ruleObserver;
    private RuleObserver ruleObserverNotApplied;
    private LastReachedTimeRepository lastReachedTimeRepository;

    @BeforeEach
    public void setUp() {

        this.condition = mock(Condition.class);
        this.conditionNotApplied = mock(Condition.class);
        this.ruleObserver = mock(RuleObserver.class);
        this.ruleObserverNotApplied = mock(RuleObserver.class);

        this.ruleRepository = mock(RuleRepository.class);
        this.ruleObserverRepository = mock(RuleObserverRepository.class);
        this.lastReachedTimeRepository = mock(LastReachedTimeRepository.class);

        given(condition.apply(any())).willReturn(Boolean.TRUE);
        given(conditionNotApplied.apply(any())).willReturn(Boolean.FALSE);

        given(ruleRepository.findAllByCustomerIdAndDeviceId("customerId", "deviceId"))
                .willReturn(Arrays.asList(
                        new Rule("x", Arrays.asList(condition)),
                        new Rule("y", Arrays.asList(conditionNotApplied))
                ));
        given(ruleObserverRepository.findAllByRuleId("x"))
                .willReturn(Arrays.asList(ruleObserver));

        given(ruleObserverRepository.findAllByRuleId("y"))
                .willReturn(Arrays.asList(ruleObserverNotApplied));

        this.ruleEngineService = new RuleEngineServiceImpl(
                ruleRepository,
                ruleObserverRepository,
                lastReachedTimeRepository);

    }

    @Test
    public void multi_condition_rule() {
        this.ruleEngineService.applyRules(createNumericIngestionData(1));

        verify(this.ruleRepository, times(1)).findAllByCustomerIdAndDeviceId("customerId", "deviceId");
        verify(this.ruleObserverRepository, times(1)).findAllByRuleId("x");
        verify(this.ruleObserverRepository, times(1)).findAllByRuleId("y");
        verify(this.condition, times(1)).apply(any());
        verify(this.conditionNotApplied, times(1)).apply(any());
        verify(this.ruleObserver, times(1)).apply(any());
        verify(this.ruleObserverNotApplied, times(0)).apply(any());

    }
}
