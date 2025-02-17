package com.giunne.itemservice.domain.item.application.dto.response;

import com.giunne.itemservice.domain.item.domain.type.ItemGrade;
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
@Schema(description = "아이템 검색 응답DTO")
public class GetItemPageResponseDto {
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
//    @Schema(description = "카테고리 명" ,
//            example = "옷")
//    private String categoryName;
//    @Schema(description = "상점 ID" ,
//            example = "1")
//    private String storeId;
//    @Schema(description = "상점 ID" ,
//            example = "기운내 상점")
//    private String storeIdName;
    @Schema(description = "아이템 등급" ,
            example = "B")
    private ItemGrade itemGrade;
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
        @Schema(description = "이미지 위치")
        private List<ItemImagePosition> itemImagePositions = new ArrayList<>();

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Schema(description = "아이템 아미지 위치 검색 응답DTO")
        public static class ItemImagePosition{
            @Schema(description = "이미지 위치 ID" ,
                    example = "1")
            private Long id;
            @Schema(description = "x좌표" ,
                    example = "50.5")
            private Double positionX;
            @Schema(description = "y좌표" ,
                    example = "100.2")
            private Double positionY;
            @Schema(description = "z좌표" ,
                    example = "0.0")
            private Double positionZ;
            @Schema(description = "레벨" ,
                    example = "6")
            private Long level;
        }
    }
}
