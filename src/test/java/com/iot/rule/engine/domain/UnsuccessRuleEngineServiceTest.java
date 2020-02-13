package com.iot.rule.engine.domain;

import com.iot.rule.engine.infra.LastReachedTimeRepository;
import com.iot.rule.engine.infra.RuleRepository;
import com.iot.rule.engine.infra.RuleObserverRepository;
import com.iot.rule.engine.infra.RuleEngineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.iot.rule.engine.helper.IngestionDataHelper.createNumericIngestionData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class UnsuccessRuleEngineServiceTest {

    private RuleEngineService ruleEngineService;

    private RuleRepository ruleRepository;
    private RuleObserverRepository ruleObserverRepository;

    private Condition condition;
    private RuleObserver ruleObserver;
    private LastReachedTimeRepository lastReachedTimeRepository;

    @BeforeEach
    public void setUp() {

        this.condition = mock(Condition.class);
        this.ruleObserver = mock(RuleObserver.class);

        this.ruleRepository = mock(RuleRepository.class);
        this.ruleObserverRepository = mock(RuleObserverRepository.class);
        this.lastReachedTimeRepository = mock(LastReachedTimeRepository.class);

        given(condition.apply(any())).willReturn(Boolean.FALSE);

        given(ruleRepository.findAllByCustomerIdAndDeviceId("customerId", "deviceId"))
                .willReturn(Arrays.asList(new Rule("x", Arrays.asList(condition))));
        given(ruleObserverRepository.findAllByRuleId(any()))
                .willReturn(Arrays.asList(ruleObserver));

        this.ruleEngineService = new RuleEngineServiceImpl(
                ruleRepository,
                ruleObserverRepository,
                lastReachedTimeRepository);

    }

    @Test
    public void one_condition_rule() {
        this.ruleEngineService.applyRules(createNumericIngestionData(1));

        verify(this.ruleRepository, times(1)).findAllByCustomerIdAndDeviceId("customerId", "deviceId");
        verify(this.ruleObserverRepository, times(1)).findAllByRuleId("x");
        verify(this.condition, times(1)).apply(any());
        verify(this.ruleObserver, times(0)).apply(any());
        verify(this.lastReachedTimeRepository, times(0)).findByRuleId(any());

    }
}
