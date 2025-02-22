package com.giunne.commonservice.config;

import com.giunne.commonservice.error.FeignClientExceptionErrorDecoder;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * OpenFeign Config
 */
@Configuration
@EnableFeignClients(basePackages = "com.giunne")
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    // 로깅 레벨 설정
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    // 1번에서 만든 FeignClientExceptionErrorDecoder 빈으로 등록
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientExceptionErrorDecoder();
    }

    // 요청 재시도
    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 2000, 3);
    }

}