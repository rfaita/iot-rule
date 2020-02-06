package com.iot.rule.engine.application.config;

import com.iot.rule.engine.application.repository.RuleNotificationRepository;
import com.iot.rule.engine.application.repository.RuleObservableRepositoryImpl;
import com.iot.rule.engine.application.repository.RuleRepositoryImpl;
import com.iot.rule.engine.application.repository.RuleRepresentationRepository;
import com.iot.rule.engine.domain.RuleEngineServiceImpl;
import com.iot.rule.engine.infra.LastConditionReachedTimeRepository;
import com.iot.rule.engine.infra.RuleObservableRepository;
import com.iot.rule.engine.infra.RuleEngineService;
import com.iot.rule.engine.infra.RuleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public RuleEngineService ruleEngineService(RuleRepository ruleRepository,
                                               RuleObservableRepository notificationRepository,
                                               LastConditionReachedTimeRepository lastConditionReachedTimeRepository) {
        return new RuleEngineServiceImpl(ruleRepository,
                notificationRepository,
                lastConditionReachedTimeRepository);
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
