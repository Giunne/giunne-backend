package com.giunne.commonservice.infra.external.domain.member.client.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "나의 학교 정보 조회 응답DTO")
public class GetMySchoolInfoResponseDto {
    @Schema(description = "학교 id",
            example = "1000")
    private Long schoolId;
    @Schema(description = "학교명",
            example = "서울초등학교")
    private String schoolName;
    @Schema(
            description = "학년",
            example = "1"
    )
    private Integer grade;
    @Schema(
            description = "반",
            example = "1"
    )
    private Integer classNumber;
    @Schema(
            description = "번호",
            example = "10",
            nullable = true
    )
    private Integer studentNumber;
}
