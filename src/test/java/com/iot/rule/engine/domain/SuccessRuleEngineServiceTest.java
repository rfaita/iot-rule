package com.iot.rule.engine.domain;

import com.iot.rule.engine.helper.IngestionDataHelper;
import com.iot.rule.engine.infra.RuleObservableRepository;
import com.iot.rule.engine.infra.RuleEngineService;
import com.iot.rule.engine.infra.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class SuccessRuleEngineServiceTest {

    private RuleEngineService ruleEngineService;

    private RuleRepository ruleRepository;
    private RuleObservableRepository notificationRepository;

    private Condition condition;
    private RuleObservable ruleObservable;

    @BeforeEach
    public void setUp() {

        this.condition = mock(Condition.class);
        this.ruleObservable = mock(RuleObservable.class);

        this.ruleRepository = mock(RuleRepository.class);
        this.notificationRepository = mock(RuleObservableRepository.class);

        given(condition.apply(any())).willReturn(Boolean.TRUE);

        given(ruleRepository.findAllByCustomerIdAndDeviceId("customerId", "deviceId"))
                .willReturn(Arrays.asList(new Rule("x", Arrays.asList(condition))));
        given(notificationRepository.findAllByRuleId(any()))
                .willReturn(Arrays.asList(ruleObservable));

        this.ruleEngineService = new RuleEngineServiceImpl(ruleRepository, notificationRepository);

    }

    @Test
    public void one_condition_rule() {
        this.ruleEngineService.applyRules(IngestionDataHelper.createNumericIngestionData(1));

        verify(this.ruleRepository, times(1))
                .findAllByCustomerIdAndDeviceId("customerId", "deviceId");
        verify(this.notificationRepository, times(1)).findAllByRuleId("x");
        verify(this.condition, times(1)).apply(any());
        verify(this.ruleObservable, times(1)).apply(any());

    }
}
