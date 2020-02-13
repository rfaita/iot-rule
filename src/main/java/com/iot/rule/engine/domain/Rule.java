package com.iot.rule.engine.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.iot.rule.engine.domain.helper.TimeHelper.currentTimeMillis;

public class Rule {

    private final String id;
    private final List<Condition> conditions;
    private final List<RuleObserver> ruleObservers;
    private final List<RuleBounceTimeObserver> ruleBounceTimeObservers;
    private final Second bounceTime;
    private Long lastReachedTime;

    public Rule(final String id, final List<Condition> conditions, Second bounceTime) {
        this.id = id;
        this.conditions = new ArrayList<>(conditions);
        this.ruleObservers = new ArrayList<>();
        this.ruleBounceTimeObservers = new ArrayList<>();
        this.bounceTime = bounceTime;
    }

    public Rule(final String id, final List<Condition> conditions) {
        this.id = id;
        this.conditions = new ArrayList<>(conditions);
        this.ruleObservers = new ArrayList<>();
        this.ruleBounceTimeObservers = new ArrayList<>();
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
            setLastReachedTime();

            notifyObserves(data);
        }

    }

    private Boolean verifyRuleInBounceTime() {
        if (this.bounceTime != null && this.lastReachedTime != null) {
            return this.lastReachedTime + this.bounceTime.getMilliseconds() >= currentTimeMillis();
        }
        return Boolean.FALSE;
    }

    private void setLastReachedTime() {
        this.lastReachedTime = currentTimeMillis();

        notifyBounceTimeObserves(this);

    }

    public Rule restoreLastReachedTime(Optional<Long> lastReachedTime) {
        this.lastReachedTime = lastReachedTime.orElse(null);
        return this;
    }


    public Rule addBounceObservers(List<? extends RuleBounceTimeObserver> ruleBounceTimeObservers) {
        if (ruleBounceTimeObservers != null) {
            this.ruleBounceTimeObservers.addAll(ruleBounceTimeObservers);
        }
        return this;
    }

    public Rule addBounceObserver(RuleBounceTimeObserver ruleBounceTimeObserver) {
        this.ruleBounceTimeObservers.add(ruleBounceTimeObserver);
        return this;
    }


    private void notifyBounceTimeObserves(Rule rule) {
        this.ruleBounceTimeObservers
                .forEach(ruleBounceTimeObserver -> ruleBounceTimeObserver.apply(rule));
    }

    public Rule addObservers(List<? extends RuleObserver> ruleObservers) {
        if (ruleObservers != null) {
            this.ruleObservers.addAll(ruleObservers);
        }
        return this;
    }

    public Rule addObserver(RuleObserver ruleObserver) {
        this.ruleObservers.add(ruleObserver);
        return this;
    }

    private void notifyObserves(final IngestionData data) {

        this.ruleObservers.forEach(ruleObserver -> ruleObserver.apply(data));

    }

    public Long getLastReachedTime() {
        return lastReachedTime;
    }
}
