package com.giunne.memberservice.domain.auth.ui;


import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.auth.application.AuthService;
import com.giunne.memberservice.domain.auth.application.dto.request.CreateStudentAuthRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.request.CreateTeacherAuthRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.request.GetLoginIdValidationRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.response.GetLoginIdValidationResponseDto;
import com.giunne.memberservice.domain.auth.application.dto.response.MemberAccessTokenResponseDto;
import com.giunne.memberservice.domain.member.repository.entity.MemberEntity;
import com.giunne.memberservice.domain.member.repository.jpa.JpaMemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "회원가입", description = "선생님 및 학생 회원가입")
@RestController
@RequestMapping("/v1/api/member/auth")
@RequiredArgsConstructor
public class SignUpController {

    private final AuthService authService;
    private final JpaMemberRepository jpaMemberRepository;

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

    @Operation(summary = "학생 회원가입", description = """
            ## 기능설명
            * 학생 회원가입
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/signup/student")
    public Response<MemberAccessTokenResponseDto> registerStudent(@RequestBody CreateStudentAuthRequestDto dto) {
        return Response.ok(authService.registerStudent(dto));
    }

    @Operation(summary = "아이디 중복 확인", description = """
            ## 기능설명
            * 아이디 중복 확인
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/duplicate-id")
    public Response<GetLoginIdValidationResponseDto> validationLoginId(@ParameterObject GetLoginIdValidationRequestDto dto) {
        Optional<MemberEntity> optionalMemberEntity = jpaMemberRepository.findByLoginId_LoginId(dto.loginId());
        GetLoginIdValidationResponseDto getLoginIdValidationResponseDto = GetLoginIdValidationResponseDto.builder()
                .isPresent(optionalMemberEntity.isPresent())
                .build();
        return Response.ok(getLoginIdValidationResponseDto);
    }
}
