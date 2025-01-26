package com.giunne.memberservice.domain.school.api.response;

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
public class GetSchoolPageResponseDto {
    @Schema(description = "학교 id",
            example = "1000")
    private Long id;
    @Schema(description = "학교명" ,
            example = "서울초등학교")
    private String schoolNm;
    @Schema(description = "소재지도로명주소" ,
            example = "서울특별시 관악구 문성로16길 40")
    private String rdnmadr;
}
