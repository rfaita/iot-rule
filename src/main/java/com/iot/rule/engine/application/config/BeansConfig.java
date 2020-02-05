package com.iot.rule.engine.application.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.rule.engine.application.repository.RuleNotificationRepository;
import com.iot.rule.engine.application.repository.RuleObservableRepositoryImpl;
import com.iot.rule.engine.application.repository.RuleRepositoryImpl;
import com.iot.rule.engine.application.repository.RuleRepresentationRepository;
import com.iot.rule.engine.domain.RuleEngineServiceImpl;
import com.iot.rule.engine.infra.RuleObservableRepository;
import com.iot.rule.engine.infra.RuleEngineService;
import com.iot.rule.engine.infra.RuleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public RuleEngineService ruleEngineService(RuleRepository ruleRepository,
                                               RuleObservableRepository notificationRepository) {
        return new RuleEngineServiceImpl(ruleRepository, notificationRepository);
    }

    @Bean
    public RuleRepository ruleRepository(RuleRepresentationRepository ruleRepresentationRepository) {
        return new RuleRepositoryImpl(ruleRepresentationRepository);
    }

    @Bean
    public RuleObservableRepository ruleObservableRepository(RuleNotificationRepository ruleNotificationRepository) {
        return new RuleObservableRepositoryImpl(ruleNotificationRepository);
    }
}
