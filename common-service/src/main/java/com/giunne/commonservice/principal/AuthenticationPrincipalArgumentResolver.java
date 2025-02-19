package com.giunne.commonservice.principal;


import com.giunne.commonservice.jwt.service.TokenManager;
import com.giunne.commonservice.domain.auth.MemberRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenManager tokenManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasMemberInfoAnnotation = parameter.hasParameterAnnotation(AuthPrincipal.class);
        boolean hasMemberInfoDto = MemberPrincipal.class.isAssignableFrom(parameter.getParameterType());
        return hasMemberInfoAnnotation && hasMemberInfoDto;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        try {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || authorizationHeader.split(" ").length != 2) {
                throw new IllegalArgumentException("Invalid token");
            }
            String token = authorizationHeader.split(" ")[1];

            Claims tokenClaims = tokenManager.getTokenClaims(token);
            Long memberId = Long.valueOf((Integer) tokenClaims.get("memberId"));
            String role = (String) tokenClaims.get("role");

            return MemberPrincipal.builder()
                    .memberId(memberId)
                    .role(role)
                    .build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token");
        }
    }

}
