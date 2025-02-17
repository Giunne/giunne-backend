package com.giunne.itemservice.domain.item.application.dto.request;

import com.giunne.commonservice.domain.common.Pageable;
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
@Schema(description = "아이템 검색 요청DTO")
public class GetItemPageRequestDto extends Pageable {
    @Parameter(
            description = "카테고리 ID",
            example = "1"
    )
    @NotNull
    private Long categoryId;
}
