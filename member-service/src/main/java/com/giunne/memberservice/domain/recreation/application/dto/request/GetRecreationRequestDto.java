package com.giunne.memberservice.domain.recreation.application.dto.request;

import com.giunne.commonservice.domain.common.Pageable;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "레크레이션 검색 요청DTO")
public class GetRecreationRequestDto extends Pageable {
    @Schema(
            description = "레크레이션명",
            example = "기운내보자~~",
            nullable = true
    )
    private String recreationName;
//
//    @Parameter(
//            description = "레크레이션 코드",
//            example = "123456789012345(15자리)"
//    )
//    private String recreationCode;
//
//    @Parameter(
//            description = "기수번호",
//            example = "1"
//    )
//    private Long baseNumber;

}
