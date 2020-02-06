package com.iot.rule.engine.domain;

import java.util.ArrayList;
import java.util.List;

import static com.iot.rule.engine.domain.helper.TimeHelper.currentTimeMillis;

public class Rule {

    private final String id;
    private final List<Condition> conditions;
    private final List<RuleObservable> ruleObservables;
    private final List<RuleBounceTimeObservable> ruleBounceTimeObservables;
    private final Second bounceTime;
    private Long lastConditionReachedTime;

    public Rule(final String id, final List<Condition> conditions, Second bounceTime) {
        this.id = id;
        this.conditions = new ArrayList<>(conditions);
        this.ruleObservables = new ArrayList<>();
        this.ruleBounceTimeObservables = new ArrayList<>();
        this.bounceTime = bounceTime;
    }

    public Rule(final String id, final List<Condition> conditions) {
        this.id = id;
        this.conditions = new ArrayList<>(conditions);
        this.ruleObservables = new ArrayList<>();
        this.ruleBounceTimeObservables = new ArrayList<>();
        this.bounceTime = null;
    }

    public String getId() {
        return id;
    }

    public Boolean hasBounceTime() {
        return this.bounceTime != null;
    }

    public void apply(final IngestionData data) {

        Boolean conditionsResult = Boolean.TRUE;
        for (Condition condition : this.conditions) {
            conditionsResult &= condition.apply(data);
        }

        if (conditionsResult && !verifyRuleInBounceTime()) {
            setLastConditionReachedTime();

            notifyObserves(data);
        }

    }

    private Boolean verifyRuleInBounceTime() {
        if (this.bounceTime != null && this.lastConditionReachedTime != null) {
            return this.lastConditionReachedTime + this.bounceTime.getMilliseconds() >= currentTimeMillis();
        }
        return Boolean.FALSE;
    }

    private void setLastConditionReachedTime() {
        this.lastConditionReachedTime = currentTimeMillis();

        notifyBounceTimeObserves(this);

    }

    public void restoreLastConditionReachedTime(Long lastConditionReachedTime) {
        this.lastConditionReachedTime = lastConditionReachedTime;
    }


    public Rule addBounceObservers(List<? extends RuleBounceTimeObservable> ruleBounceTimeObservables) {
        if (ruleBounceTimeObservables != null) {
            this.ruleBounceTimeObservables.addAll(ruleBounceTimeObservables);
        }
        return this;
    }

    public Rule addBounceObserver(RuleBounceTimeObservable ruleBounceTimeObservable) {
        this.ruleBounceTimeObservables.add(ruleBounceTimeObservable);
        return this;
    }


    private void notifyBounceTimeObserves(Rule rule) {
        this.ruleBounceTimeObservables
                .forEach(ruleBounceTimeObservable -> ruleBounceTimeObservable.apply(rule));
    }

    public Rule addObservers(List<? extends RuleObservable> ruleObservables) {
        if (ruleObservables != null) {
            this.ruleObservables.addAll(ruleObservables);
        }
        return this;
    }

    public Rule addObserver(RuleObservable ruleObservable) {
        this.ruleObservables.add(ruleObservable);
        return this;
    }

    private void notifyObserves(final IngestionData data) {

        this.ruleObservables.forEach(ruleObservable -> ruleObservable.apply(data));

    }

    public Long getLastConditionReachedTime() {
        return lastConditionReachedTime;
    }
}
