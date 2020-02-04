package com.iot.rule.engine.infra;

import com.iot.rule.engine.domain.IngestionData;

public interface RuleEngineService {

    void applyRules(IngestionData ingestionData);

}
