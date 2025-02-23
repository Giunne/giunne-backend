package com.giunne.itemservice.domain.item.application.dto.response;

import com.giunne.commonservice.domain.item.ItemGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "아이템 번호 검색 응답DTO")
public class ItemInfoResponseDto {
    @Schema(description = "아이템 ID",
            example = "1000")
    private Long id;
    @Schema(description = "아이템명" ,
            example = "경찰복")
    private String itemName;
    @Schema(description = "아이템 설명" ,
            example = "아이템 설명")
    private String itemDescription;
    @Schema(description = "가격" ,
            example = "1000")
    private Long price;
    @Schema(description = "필수 레벨" ,
            example = "3")
    private Long needLevel;
    @Schema(description = "순서번호(낮은숫자가 우선순위 높음)" ,
            example = "1")
    private Long sortSeq;
    @Schema(description = "카테고리 번호" ,
            example = "1")
    private Long categoryId;
    @Schema(description = "아이템 등급" ,
            example = "B")
    private ItemGrade itemGrade;
}
