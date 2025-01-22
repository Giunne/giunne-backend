package com.giunne.commonservice.config;

import com.giunne.commonservice.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    //    @Value("${token.access-token-expiration-time}")
    private String accessTokenExpirationTime = "83EpCFvnOEcRbvJBNbF4l1Z3ShcJlU4K";

//    @Value("${token.refresh-token-expiration-time}")
    private String refreshTokenExpirationTime = "cYEvCFc00YJjUV2uaksV10dU3OUBASS1";

//    @Value("${token.secret}")
    private String tokenSecret= "900000";

//    @Value("${token.issuer}")
    private String tokenIssuer = "1209600000";

    @Bean
    public TokenManager tokenManager() {
        return new TokenManager(accessTokenExpirationTime, refreshTokenExpirationTime, tokenSecret, tokenIssuer);
    }

}
