package com.giunne.memberservice.domain.auth.ui;


import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.auth.application.AuthService;
import com.giunne.memberservice.domain.auth.application.dto.request.CreateTeacherAuthRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.response.MemberAccessTokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입", description = "선생님 및 학생 회원가입")
@RestController
@RequestMapping("/v1/api/member/auth")
@RequiredArgsConstructor
public class SignUpController {

    private final AuthService authService;

    @Operation(summary = "선생님 회원가입", description = """
            ## 기능설명
            * 선생님 회원가입
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/signup/teacher")
    public Response<MemberAccessTokenResponseDto> registerTeacher(@RequestBody CreateTeacherAuthRequestDto dto) {
        return Response.ok(authService.registerTeacher(dto));
    }

//    @PostMapping("/signup/student")
//    public Response<MemberAccessTokenResponseDto> registerStudent(@RequestBody CreateStudentRequestDto dto) {
//        return Response.ok(authService.registerStudent(dto));
//    }
}
