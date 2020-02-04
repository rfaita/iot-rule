package com.iot.rule.engine.application.model;

import com.iot.rule.engine.domain.Condition;

public interface ConditionRepresentation<T> {

    Condition<T> toCondition();
}
