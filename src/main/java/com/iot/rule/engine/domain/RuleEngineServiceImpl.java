package com.iot.rule.engine.domain;

import com.iot.rule.engine.infra.RuleObservableRepository;
import com.iot.rule.engine.infra.RuleRepository;
import com.iot.rule.engine.infra.RuleEngineService;

import java.util.List;

public class RuleEngineServiceImpl implements RuleEngineService {

    private final RuleRepository ruleRepository;
    private final RuleObservableRepository notificationRepository;

    public RuleEngineServiceImpl(RuleRepository ruleRepository,
                                 RuleObservableRepository notificationRepository) {
        this.ruleRepository = ruleRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void applyRules(final IngestionData ingestionData) {

        List<Rule> rules = ruleRepository.findAllByCustomerId(ingestionData.getCustomerId());
        rules.stream()
                .peek(rule -> rule.addObservers(notificationRepository.findAllByRuleId(rule.getId())))
                .peek(rule -> rule.apply(ingestionData))
                .forEach(System.out::println);

    }
}
