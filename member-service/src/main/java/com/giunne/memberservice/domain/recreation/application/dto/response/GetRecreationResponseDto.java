package com.giunne.memberservice.domain.recreation.application.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "학교명 검색 응답DTO")
public class GetRecreationResponseDto{
        @Schema(
                description = "레크레이션 ID",
                example = "1000"
        )
        Long id;
        @Schema(
                description = "레크레이션 이름",
                example = "기능성 운동 교실"
        )
        String recreationName;
        @Schema(
                description = "레크레이션 코드",
                example = "123456789012345(15자리)"
        )
        String recreationCode;
        @Schema(
                description = "선생님 회원 번호",
                example = "123"
        )
        Long teacherId;
        @Schema(
                description = "선생님 로그인ID",
                example = "LoginID"
        )
        String teacherLoginId;
        @Schema(
                description = "선생님명",
                example = "허태식"
        )
        String teacherName;

}
