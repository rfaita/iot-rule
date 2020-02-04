package com.iot.rule.engine.application.repository;

import com.iot.rule.engine.application.model.RuleRepresentation;
import com.iot.rule.engine.domain.Rule;
import com.iot.rule.engine.infra.RuleRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RuleRepositoryImpl implements RuleRepository {

    private final RuleRepresentationRepository ruleRepresentationRepository;

    public RuleRepositoryImpl(RuleRepresentationRepository ruleRepresentationRepository) {
        this.ruleRepresentationRepository = ruleRepresentationRepository;
    }

    @Override
    public List<Rule> findAllByCustomerId(String customerId) {

        return this.ruleRepresentationRepository.findAllByCustomerId(customerId)
                .stream()
                .map(RuleRepresentation::toRule)
                .collect(Collectors.toList());

    }
}
