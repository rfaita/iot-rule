package com.iot.rule.engine.domain;

import com.iot.rule.engine.helper.IngestionDataHelper;
import com.iot.rule.engine.infra.LastReachedTimeRepository;
import com.iot.rule.engine.infra.RuleEngineService;
import com.iot.rule.engine.infra.RuleObserverRepository;
import com.iot.rule.engine.infra.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class BounceRuleEngineServiceTest {

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
        given(condition.apply(any())).willReturn(Boolean.TRUE);
        this.lastReachedTimeRepository = mock(LastReachedTimeRepository.class);

        given(ruleRepository.findAllByCustomerIdAndDeviceId("customerId", "deviceId"))
                .willReturn(Arrays.asList(new Rule("x", Arrays.asList(condition), Second.of(1))));
        given(ruleObserverRepository.findAllByRuleId(any()))
                .willReturn(Arrays.asList(ruleObserver));

        this.ruleEngineService = new RuleEngineServiceImpl(
                ruleRepository,
                ruleObserverRepository,
                lastReachedTimeRepository);

    }

    @Test
    public void bounce_rule_test() {
        this.ruleEngineService.applyRules(IngestionDataHelper.createNumericIngestionData(1));

        given(lastReachedTimeRepository.findByRuleId("x")).willReturn(Optional.of(System.currentTimeMillis()));

        this.ruleEngineService.applyRules(IngestionDataHelper.createNumericIngestionData(1));


        verify(this.ruleRepository, times(2))
                .findAllByCustomerIdAndDeviceId("customerId", "deviceId");
        verify(this.ruleObserverRepository, times(2)).findAllByRuleId("x");
        verify(this.condition, times(2)).apply(any());
        verify(this.ruleObserver, times(1)).apply(any());
        verify(this.lastReachedTimeRepository, times(2)).findByRuleId("x");
        verify(this.lastReachedTimeRepository, times(1)).save(eq("x"), any(Long.class));

    }
}
