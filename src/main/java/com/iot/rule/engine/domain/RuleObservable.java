package com.iot.rule.engine.domain;

public interface RuleObservable {

    void apply(IngestionData data);

}

