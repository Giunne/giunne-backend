package com.giunne.itemservice.domain.orders.api.response;

import com.giunne.commonservice.domain.item.ItemGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "아이템 뽑기 주문 응답DTO")
public class GetItemOrderGachaResponseDto {
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
    @Schema(description = "썸네일 URL")
    private String thumbnailUrl;
    @Schema(description = "아이템 이미지")
    private List<ItemImage> itemImages = new ArrayList<>();


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "이미지 검색 응답DTO")
    public static class ItemImage{
        @Schema(description = "이미지 ID" ,
                example = "1")
        private Long id;
        @Schema(description = "이미지 URL" ,
                example = "web/shop/clothes/[7]경찰복.PNG")
        private String fileUrl;
        @Schema(description = "대표 이미지 여부" ,
                example = "true")
        private Boolean isRepresent;
        @Schema(description = "레벨",
                example = "1")
        private Long level;
    }
}
