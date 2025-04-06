package com.giunne.memberservice.domain.notice.application.dto.request;

import com.giunne.commonservice.domain.common.Pageable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "공지 조회 요청DTO")
public class GetNoticeRequestDto extends Pageable {
    @Schema(description = "레크레이션 ID",
            example = "1000")
    private Long recreationId;
}
