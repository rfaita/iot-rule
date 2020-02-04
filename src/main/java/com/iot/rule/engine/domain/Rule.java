package com.iot.rule.engine.domain;

import java.util.ArrayList;
import java.util.List;

public class Rule {

    private final String id;
    private final List<Condition> conditions;
    private final List<RuleObservable> ruleObservables;

    public Rule(final String id, final List<Condition> conditions) {
        this.id = id;
        this.conditions = new ArrayList<>(conditions);
        this.ruleObservables = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void apply(final IngestionData data) {

        Boolean conditionsResult = Boolean.TRUE;
        for (Condition condition : this.conditions) {
            conditionsResult &= condition.apply(data);
        }

        if (conditionsResult) {
            notifyObserves(data);
        }

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
