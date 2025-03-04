package com.giunne.itemservice.domain.orders.api.request;

import com.giunne.itemservice.domain.item.domain.type.GachaType;
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
@Schema(description = "카테고리별 인벤토리 조회DTO")
public class GetItemOrderGachaRequestDto {
    @Parameter(
            description = "뽑기타입"
    )
    @NotNull
    private GachaType gachaTypes;
}
