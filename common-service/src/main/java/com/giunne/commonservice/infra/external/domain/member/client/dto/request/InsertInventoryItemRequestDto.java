package com.giunne.commonservice.infra.external.domain.member.client.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "인벤토리 아이템 추가 요청DTO")
public class InsertInventoryItemRequestDto {
    @Parameter(
            description = "아이템 ID",
            example = "1000"
    )
    private Long id;
    @Parameter(
            description =  "아이템명" ,
            example = "경찰복"
    )
    private String itemName;
    @Parameter(
            description = "카테고리 ID",
            example = "1"
    )
    private Long categoryId;
    @Parameter(
            description = "카테고리 ID",
            example = "1"
    )
    private Long playerId;
}
