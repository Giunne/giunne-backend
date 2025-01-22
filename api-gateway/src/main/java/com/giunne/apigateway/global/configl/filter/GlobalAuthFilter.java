package com.giunne.apigateway.global.configl.filter;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;


@Component
@Slf4j
public class GlobalAuthFilter extends AbstractGatewayFilterFactory<GlobalAuthFilter.Config> {

    @Value("${token.secret}")
    private String tokenSecret;

    private static final List<String> excludeUris = List.of(

            );

    public GlobalAuthFilter(final StringRedisTemplate redisTemplate) {
        super(Config.class);
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(GlobalAuthFilter.Config config) {
        return (exchange, chain) -> {
            final ServerHttpRequest request = exchange.getRequest();
            final String path = request.getURI().getPath();

            for (String excludeUri : excludeUris) {
                if (excludeUri.equals(path)) {
                    log.info("Excluded URI: {}",excludeUri);
                    return chain.filter(exchange);
                }
            }

//            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                return failAuthenticationResponse(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
//            }
//
//            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//            String jwt = authorizationHeader.replace("Bearer ", "");
//
//            if (!isJwtValid(jwt)) {
//                return failAuthenticationResponse(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
//            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> failAuthenticationResponse(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);

        byte[] bytes = "The requested token is invalid.".getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }

    private boolean isJwtValid(String jwt) {
        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        boolean returnValue = true;
        String subject = null;

        try {
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build();

            subject = jwtParser.parseClaimsJws(jwt).getBody().getSubject();
        } catch (Exception ex) {
            returnValue = false;
        }

        if (subject == null || subject.isEmpty()) {
            returnValue = false;
        }

        return returnValue;
    }
}
