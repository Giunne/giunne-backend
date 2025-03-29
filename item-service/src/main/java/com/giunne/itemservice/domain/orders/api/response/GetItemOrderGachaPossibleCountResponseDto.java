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
@Schema(description = "뽑기 가능 아이템 수 조회 응답DTO")
public class GetItemOrderGachaPossibleCountResponseDto {
    @Schema(description = "뽑기 가능 아이템 수",
            example = "50")
    private Long possibleCount;

}
