package com.iot.rule.engine.application.config;

import com.iot.rule.engine.application.repository.*;
import com.iot.rule.engine.application.repository.impl.LastReachedTimeRepositoryImpl;
import com.iot.rule.engine.application.repository.impl.RuleObservableRepositoryImpl;
import com.iot.rule.engine.application.repository.impl.RuleRepositoryImpl;
import com.iot.rule.engine.domain.RuleEngineServiceImpl;
import com.iot.rule.engine.infra.LastReachedTimeRepository;
import com.iot.rule.engine.infra.RuleObservableRepository;
import com.iot.rule.engine.infra.RuleEngineService;
import com.iot.rule.engine.infra.RuleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class BeansConfig {

    @Bean
    public RuleEngineService ruleEngineService(RuleRepository ruleRepository,
                                               RuleObservableRepository notificationRepository,
                                               LastReachedTimeRepository lastReachedTimeRepository) {
        return new RuleEngineServiceImpl(ruleRepository,
                notificationRepository,
                lastReachedTimeRepository);
    }

    @Bean
    public RuleRepository ruleRepository(RuleRepresentationRepository ruleRepresentationRepository) {
        return new RuleRepositoryImpl(ruleRepresentationRepository);
    }

    @Bean
    public RuleObservableRepository ruleObservableRepository(RuleNotificationRepository ruleNotificationRepository) {
        return new RuleObservableRepositoryImpl(ruleNotificationRepository);
    }

    @Bean
    public LastReachedTimeRepository lastConditionReachedTimeRepository(
            RedisTemplate<String, Long> redisTemplate){
        return new LastReachedTimeRepositoryImpl(redisTemplate);
    }
}
