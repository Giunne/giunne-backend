package com.giunne.memberservice.domain.auth.ui;


import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.commonservice.util.AuthorizationHeaderUtils;
import com.giunne.memberservice.domain.auth.application.AuthService;
import com.giunne.memberservice.domain.auth.application.dto.request.LoginRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.response.AccessTokenResponseDto;
import com.giunne.memberservice.domain.auth.application.dto.response.MemberAccessTokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인", description = "선생님 및 학생 로그인")
@RestController
@RequestMapping("/v1/api/member/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = """
            ## 기능설명
            * 로그인
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/login")
    public Response<MemberAccessTokenResponseDto> login(@RequestBody LoginRequestDto dto) {
        return Response.ok(authService.loginMember(dto));
    }

    @Operation(summary = "로그아웃", description = "start/v1/member/logout\n\n" )
    @PostMapping("/logout")
    public Response<String> logout(@AuthPrincipal @Parameter(hidden=true) MemberPrincipal memberPrincipal, HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

        String accessToken = authorizationHeader.split(" ")[1];
        authService.logout(accessToken);
        return Response.ok("Logout success");
    }

    @Operation(summary = "refresh-token으로 access-token 재발급", description = "start/v1/member/access-token/issue\n\n" )
    @PostMapping("/access-token/issue")
    public Response<AccessTokenResponseDto> reissueAccessToken(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

        String refreshToken = authorizationHeader.split(" ")[1];
        AccessTokenResponseDto response = authService.createAccessTokenByRefreshToken(refreshToken);
        return Response.ok(response);
    }

}
