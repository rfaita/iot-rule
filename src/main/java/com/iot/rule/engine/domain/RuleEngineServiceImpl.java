package com.iot.rule.engine.domain;

import com.iot.rule.engine.infra.LastReachedTimeRepository;
import com.iot.rule.engine.infra.RuleObserverRepository;
import com.iot.rule.engine.infra.RuleRepository;
import com.iot.rule.engine.infra.RuleEngineService;

import java.util.stream.Stream;

public class RuleEngineServiceImpl implements RuleEngineService {

    private final RuleRepository ruleRepository;
    private final RuleObserverRepository notificationRepository;
    private final LastReachedTimeRepository lastReachedTimeRepository;
    private final PersistentRuleBounceTimeObserver persistentRuleBounceTimeObserver;

    public RuleEngineServiceImpl(RuleRepository ruleRepository,
                                 RuleObserverRepository notificationRepository,
                                 LastReachedTimeRepository lastReachedTimeRepository) {
        this.ruleRepository = ruleRepository;
        this.notificationRepository = notificationRepository;
        this.lastReachedTimeRepository = lastReachedTimeRepository;

        this.persistentRuleBounceTimeObserver =
                new PersistentRuleBounceTimeObserver(this.lastReachedTimeRepository);
    }

    @Override
    public void applyRules(final IngestionData ingestionData) {

        Stream<Rule> rules = ruleRepository.findAllByCustomerIdAndDeviceId(
                ingestionData.getCustomerId(),
                ingestionData.getDeviceId()
        ).stream();

        rules = this.addBounceObservers(rules);

        rules = this.addObservers(rules);

        rules = this.restoreLastReachedTime(rules);

        this.applyRules(rules, ingestionData);
    }

    private Stream<Rule> addObservers(Stream<Rule> rules) {
        return rules
                .map(rule -> rule.addObservers(notificationRepository.findAllByRuleId(rule.getId())));
    }

    private Stream<Rule> addBounceObservers(Stream<Rule> rules) {
        return rules
                .map(rule ->
                        rule.hasBounceTime()
                                ? rule.addBounceObserver(this.persistentRuleBounceTimeObserver)
                                : rule);
    }

    private Stream<Rule> restoreLastReachedTime(Stream<Rule> rules) {
        return rules
                .map(rule ->
                        rule.hasBounceTime()
                                ? rule.restoreLastReachedTime(this.lastReachedTimeRepository.findByRuleId(rule.getId()))
                                : rule);
    }

    private void applyRules(Stream<Rule> rules, IngestionData ingestionData) {
        rules.forEach(rule -> rule.apply(ingestionData));

    }
}
