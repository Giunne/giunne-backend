package com.giunne.commonservice.config;

import com.giunne.commonservice.interceptor.AdminAuthorizationInterceptor;
import com.giunne.commonservice.interceptor.AuthenticationInterceptor;
import com.giunne.commonservice.principal.AuthenticationPrincipalArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.Arrays;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final AdminAuthorizationInterceptor adminAuthorizationInterceptor;
    private final AuthenticationPrincipalArgumentResolver memberInfoArgumentResolver;

    //CORS 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost",
                "http://localhost:9001",
                "http://192.168.1.90:9001",
                "http://localhost",
                "https://localhost"));

        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type"));
        configuration.setAllowCredentials(true); // 허용된 도메인에 쿠키를 전송하도록 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .order(1) // 가장 먼저 인증 인터셉터가 실행
                .addPathPatterns("/v1/api/**")
                .excludePathPatterns("/v1/api/oauth/login",
                        "/v1/api/access-token/issue",
                        "/v1/api/logout",
                        "/v1/api/health",
                        "/v1/api/members/health-check",
                        "/v1/api/members/register",
                        "/v1/api/members/login",
                        "/v1/api/member/**",
                        "/v1/api/items/**",
                        "/v1/api/quest/**",
                        "/v1/api/community/**"
                        );

        registry.addInterceptor(adminAuthorizationInterceptor)
                .order(2)
                .addPathPatterns("/api/admin/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberInfoArgumentResolver);
    }

}
