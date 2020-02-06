package com.iot.rule.engine.domain;

import com.iot.rule.engine.infra.LastConditionReachedTimeRepository;

class PersistentRuleBounceTimeObserver implements RuleBounceTimeObservable {

    private final LastConditionReachedTimeRepository repository;

    public PersistentRuleBounceTimeObserver(LastConditionReachedTimeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void apply(Rule data) {
        this.repository.save(data.getId(), data.getLastConditionReachedTime());
    }
}
