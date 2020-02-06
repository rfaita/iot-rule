package com.iot.rule.engine;

import com.iot.rule.engine.application.model.NumericConditionRepresentation;
import com.iot.rule.engine.application.model.RuleRepresentation;
import com.iot.rule.engine.application.repository.RuleRepresentationRepository;
import com.iot.rule.engine.domain.Condition;
import com.iot.rule.engine.domain.Second;
import com.iot.rule.engine.domain.operator.OperatorType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class RuleEngineApplication implements CommandLineRunner {

	private final RuleRepresentationRepository repository;

	public RuleEngineApplication(RuleRepresentationRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... args) throws Exception {

		NumericConditionRepresentation condition = new NumericConditionRepresentation();
		condition.setField("temperature");
		condition.setOperatorType(OperatorType.NUMERIC_GTE);
		condition.setValue(new BigDecimal(10));

		RuleRepresentation rp = new RuleRepresentation();
		rp.setId("1");
		rp.setCustomerId("1");
		rp.setDeviceId("1");
		rp.setBounceTime(Second.of(5));
		rp.setConditions(Arrays.asList(condition));

		this.repository.save(rp);
	}

	public static void main(String[] args) {
		SpringApplication.run(RuleEngineApplication.class, args);
	}

}
