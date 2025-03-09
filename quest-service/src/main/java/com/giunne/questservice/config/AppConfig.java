package com.giunne.questservice.config;

import com.giunne.commonservice.domain.common.EnumMapper;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put("questProgress", QuestProgress.class);
        return enumMapper;
    }

}
