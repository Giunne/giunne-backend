package com.giunne.memberservice.domain.auth.ui;


import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.auth.application.AuthService;
import com.giunne.memberservice.domain.auth.application.dto.request.LoginRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.response.MemberAccessTokenResponseDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping("/login")
    public Response<MemberAccessTokenResponseDto> login(@RequestBody LoginRequestDto dto) {
        return Response.ok(authService.loginMember(dto));
    }
}
