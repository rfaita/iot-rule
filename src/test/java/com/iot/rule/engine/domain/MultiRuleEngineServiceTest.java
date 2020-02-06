package com.iot.rule.engine.domain;

import com.iot.rule.engine.infra.LastReachedTimeRepository;
import com.iot.rule.engine.infra.RuleObservableRepository;
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
    private RuleObservableRepository notificationRepository;

    private Condition condition;
    private Condition conditionNotApplied;
    private RuleObservable ruleObservable;
    private RuleObservable ruleObservableNotApplied;
    private LastReachedTimeRepository lastReachedTimeRepository;

    @BeforeEach
    public void setUp() {

        this.condition = mock(Condition.class);
        this.conditionNotApplied = mock(Condition.class);
        this.ruleObservable = mock(RuleObservable.class);
        this.ruleObservableNotApplied = mock(RuleObservable.class);

        this.ruleRepository = mock(RuleRepository.class);
        this.notificationRepository = mock(RuleObservableRepository.class);
        this.lastReachedTimeRepository = mock(LastReachedTimeRepository.class);

        given(condition.apply(any())).willReturn(Boolean.TRUE);
        given(conditionNotApplied.apply(any())).willReturn(Boolean.FALSE);

        given(ruleRepository.findAllByCustomerIdAndDeviceId("customerId", "deviceId"))
                .willReturn(Arrays.asList(
                        new Rule("x", Arrays.asList(condition)),
                        new Rule("y", Arrays.asList(conditionNotApplied))
                ));
        given(notificationRepository.findAllByRuleId("x"))
                .willReturn(Arrays.asList(ruleObservable));

        given(notificationRepository.findAllByRuleId("y"))
                .willReturn(Arrays.asList(ruleObservableNotApplied));

        this.ruleEngineService = new RuleEngineServiceImpl(
                ruleRepository,
                notificationRepository,
                lastReachedTimeRepository);

    }

    @Test
    public void multi_condition_rule() {
        this.ruleEngineService.applyRules(createNumericIngestionData(1));

        verify(this.ruleRepository, times(1)).findAllByCustomerIdAndDeviceId("customerId", "deviceId");
        verify(this.notificationRepository, times(1)).findAllByRuleId("x");
        verify(this.notificationRepository, times(1)).findAllByRuleId("y");
        verify(this.condition, times(1)).apply(any());
        verify(this.conditionNotApplied, times(1)).apply(any());
        verify(this.ruleObservable, times(1)).apply(any());
        verify(this.ruleObservableNotApplied, times(0)).apply(any());

    }
}
