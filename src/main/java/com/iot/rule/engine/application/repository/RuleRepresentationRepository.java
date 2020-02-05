package com.iot.rule.engine.application.repository;

import com.iot.rule.engine.application.model.RuleRepresentation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepresentationRepository extends MongoRepository<RuleRepresentation, String> {

    List<RuleRepresentation> findAllByCustomerIdAndDeviceId(String customerId, String deviceId);
}
