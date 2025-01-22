package com.giunne.commonservice.interceptor;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.AuthenticationException;
import com.giunne.commonservice.jwt.service.TokenManager;
import com.giunne.commonservice.domain.auth.MemberRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminAuthorizationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = authorizationHeader.split(" ")[1];

        // 이전 인터셉터(AuthenticationInterceptor) 에서 토큰 검증은 했으므로 Role 이 ADMIN 인지만 확인
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String role = (String)tokenClaims.get("role");
        if(!MemberRole.ROLE_ADMIN.equals(MemberRole.valueOf(role))) {
            throw new AuthenticationException(ErrorCode.FORBIDDEN_ADMIN);
        }

        return true;
    }

}
