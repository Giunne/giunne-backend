package com.giunne.memberservice.domain.inventory.api.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "아이템 장착 요청DTO")
public class WearingItemsRequestDto {
    @Parameter(
            description = "아이템 ID 리스트"
    )
    @NotNull
    List<Long> itemidList;
}
