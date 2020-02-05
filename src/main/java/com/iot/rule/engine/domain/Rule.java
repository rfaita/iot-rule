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

    public void apply(final IngestionData data) {

        Boolean conditionsResult = Boolean.TRUE;
        for (Condition condition : this.conditions) {
            conditionsResult &= condition.apply(data);
        }

        if (conditionsResult && verifyRuneInBounceTime()) {

            setLastConditionReachedTime();

            notifyObserves(data);
        }

    }

    private Boolean verifyRuneInBounceTime() {
        if (this.bounceTime != null) {
            return this.lastConditionReachedTime + this.bounceTime.getMilliseconds() < currentTimeMillis();
        }
        return Boolean.TRUE;
    }

    private void setLastConditionReachedTime() {
        this.lastConditionReachedTime = currentTimeMillis();

        notifyBounceTimeObserves(this.lastConditionReachedTime);

    }


    public void addRuleBounceObservers(List<? extends RuleBounceTimeObservable> ruleBounceTimeObservables) {
        if (ruleBounceTimeObservables != null) {
            this.ruleBounceTimeObservables.addAll(ruleBounceTimeObservables);
        }
    }

    public void addRuleBounceObserver(RuleBounceTimeObservable ruleBounceTimeObservable) {
        this.ruleBounceTimeObservables.add(ruleBounceTimeObservable);
    }


    private void notifyBounceTimeObserves(final Long lastConditionReachedTime) {
        this.ruleBounceTimeObservables
                .forEach(ruleBounceTimeObservable -> ruleBounceTimeObservable.apply(lastConditionReachedTime));
    }

    public void addObservers(List<? extends RuleObservable> ruleObservables) {
        if (ruleObservables != null) {
            this.ruleObservables.addAll(ruleObservables);
        }
    }

    public void addObserver(RuleObservable ruleObservable) {
        this.ruleObservables.add(ruleObservable);
    }

    private void notifyObserves(final IngestionData data) {

        this.ruleObservables.forEach(ruleObservable -> ruleObservable.apply(data));

    }

}
