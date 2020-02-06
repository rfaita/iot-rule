package com.iot.rule.engine.domain;

import com.iot.rule.engine.infra.LastConditionReachedTimeRepository;
import com.iot.rule.engine.infra.RuleObservableRepository;
import com.iot.rule.engine.infra.RuleRepository;
import com.iot.rule.engine.infra.RuleEngineService;

import java.util.List;
import java.util.stream.Collectors;

public class RuleEngineServiceImpl implements RuleEngineService {

    private final RuleRepository ruleRepository;
    private final RuleObservableRepository notificationRepository;
    private final LastConditionReachedTimeRepository lastConditionReachedTimeRepository;
    private final PersistentRuleBounceTimeObserver persistentRuleBounceTimeObserver;

    public RuleEngineServiceImpl(RuleRepository ruleRepository,
                                 RuleObservableRepository notificationRepository,
                                 LastConditionReachedTimeRepository lastConditionReachedTimeRepository) {
        this.ruleRepository = ruleRepository;
        this.notificationRepository = notificationRepository;
        this.lastConditionReachedTimeRepository = lastConditionReachedTimeRepository;

        this.persistentRuleBounceTimeObserver =
                new PersistentRuleBounceTimeObserver(this.lastConditionReachedTimeRepository);
    }

    @Override
    public void applyRules(final IngestionData ingestionData) {

        List<Rule> rules = ruleRepository.findAllByCustomerIdAndDeviceId(
                ingestionData.getCustomerId(),
                ingestionData.getDeviceId()
        );

        rules = this.addBounceObservers(rules);

        rules = this.addObservers(rules);

        this.applyRules(rules, ingestionData);
    }

    private List<Rule> addObservers(List<Rule> rules) {
        return rules.stream()
                .map(rule -> rule.addObservers(notificationRepository.findAllByRuleId(rule.getId())))
                .collect(Collectors.toList());
    }

    private List<Rule> addBounceObservers(List<Rule> rules) {
        return rules.stream()
                .map(rule ->
                        rule.hasBounceTime()
                                ? rule.addBounceObserver(this.persistentRuleBounceTimeObserver)
                                : rule)
                .collect(Collectors.toList());
    }

    private void applyRules(List<Rule> rules, IngestionData ingestionData) {
        rules.stream()
                .forEach(rule -> rule.apply(ingestionData));

    }
}
