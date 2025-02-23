package com.giunne.itemservice.domain.orders.application.dto.response;

import com.giunne.itemservice.domain.item.domain.type.GachaType;
import com.giunne.commonservice.domain.item.ItemGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "가챠 타입 응답DTO")
public class GachaTypeResponseDto {

    @Schema(description = "가챠명",
            example = "GENERAL")
    private String code;
    @Schema(description = "설명",
            example = "고급")
    private String codeName;
    @Schema(description = "가격",
            example = "100")
    private Long price;
    @Schema(description = "등급정보 및 확률")
    private Map<ItemGrade, Integer> itemGradeMap;

    public GachaTypeResponseDto(GachaType gachaType) {
        this.code = gachaType.name();
        this.codeName = gachaType.getType();
        this.price = gachaType.getPrice();
        this.itemGradeMap = gachaType.getItemGradeMap();
    }

}
