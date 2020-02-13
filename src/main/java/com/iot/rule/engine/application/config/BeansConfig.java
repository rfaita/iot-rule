package com.iot.rule.engine.application.config;

import com.iot.rule.engine.application.repository.*;
import com.iot.rule.engine.application.repository.impl.LastReachedTimeRepositoryImpl;
import com.iot.rule.engine.application.repository.impl.RuleObserverRepositoryImpl;
import com.iot.rule.engine.application.repository.impl.RuleRepositoryImpl;
import com.iot.rule.engine.domain.RuleEngineServiceImpl;
import com.iot.rule.engine.infra.LastReachedTimeRepository;
import com.iot.rule.engine.infra.RuleObserverRepository;
import com.iot.rule.engine.infra.RuleEngineService;
import com.iot.rule.engine.infra.RuleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class BeansConfig {

    @Bean
    public RuleEngineService ruleEngineService(RuleRepository ruleRepository,
                                               RuleObserverRepository notificationRepository,
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
    public RuleObserverRepository ruleObserverRepository(RuleNotificationRepository ruleNotificationRepository) {
        return new RuleObserverRepositoryImpl(ruleNotificationRepository);
    }

    @Bean
    public LastReachedTimeRepository lastConditionReachedTimeRepository(
            RedisTemplate<String, Long> redisTemplate){
        return new LastReachedTimeRepositoryImpl(redisTemplate);
    }
}
