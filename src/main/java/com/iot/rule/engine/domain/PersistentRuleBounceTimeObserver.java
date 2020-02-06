package com.iot.rule.engine.domain;

import com.iot.rule.engine.infra.LastReachedTimeRepository;

class PersistentRuleBounceTimeObserver implements RuleBounceTimeObservable {

    private final LastReachedTimeRepository repository;

    public PersistentRuleBounceTimeObserver(LastReachedTimeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void apply(Rule data) {
        this.repository.save(data.getId(), data.getLastReachedTime());
    }
}
