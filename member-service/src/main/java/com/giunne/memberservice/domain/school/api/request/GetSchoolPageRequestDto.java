package com.giunne.memberservice.domain.school.api.request;

import com.giunne.commonservice.domain.common.Pageable;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "학교명 검색 요청DTO")
public class GetSchoolPageRequestDto extends Pageable {
    @Parameter(
            description = "학교명",
            example = "서울"
    )
    @NotNull
    private String schoolNm;
}
