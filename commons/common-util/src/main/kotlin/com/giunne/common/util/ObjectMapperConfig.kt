package com.giunne.common.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConfig {


    /**
     * ObjectMapper에 Kotlin Module 등록
     */
    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper
            .registerModule(JavaTimeModule())
            //  Java 8+ 날짜/시간 API 지원을 활성화
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(
                KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false) //  null 빈 컬렉션으로 처리
                .configure(KotlinFeature.NullToEmptyMap, false) // null 빈 맵으로 처리
                .configure(KotlinFeature.NullIsSameAsDefault, false) // null 기본값과 동일하게 처리
                .configure(KotlinFeature.SingletonSupport, false) // 싱글톤 인스턴스를 지원
                .configure(KotlinFeature.StrictNullChecks, false) // 엄격한 null 체크 비활성화
                .build())
        return objectMapper
    }
}