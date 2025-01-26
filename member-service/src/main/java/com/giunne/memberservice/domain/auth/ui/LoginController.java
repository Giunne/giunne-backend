package com.giunne.memberservice.domain.auth.ui;


import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.auth.application.AuthService;
import com.giunne.memberservice.domain.auth.application.dto.request.LoginRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.response.MemberAccessTokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
