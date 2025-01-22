package com.giunne.memberservice.domain.auth.ui;


import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.auth.application.AuthService;
import com.giunne.memberservice.domain.auth.application.dto.request.CreateTeacherAuthRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.response.MemberAccessTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class SignUpController {

    private final AuthService authService;

    @PostMapping("/signup/teacher")
    public Response<MemberAccessTokenResponseDto> registerTeacher(@RequestBody CreateTeacherAuthRequestDto dto) {
        return Response.ok(authService.registerTeacher(dto));
    }

//    @PostMapping("/signup/student")
//    public Response<MemberAccessTokenResponseDto> registerStudent(@RequestBody CreateStudentRequestDto dto) {
//        return Response.ok(authService.registerStudent(dto));
//    }
}
