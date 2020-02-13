package com.iot.rule.engine.domain;

public interface RuleObserver {

    void apply(IngestionData data);

}

