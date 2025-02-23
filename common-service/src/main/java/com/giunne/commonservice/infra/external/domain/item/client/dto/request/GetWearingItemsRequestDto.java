package com.giunne.commonservice.infra.external.domain.item.client.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "장착중인 아이템 미리보기 검색 요청DTO")
public class GetWearingItemsRequestDto {
    List<Long> itemIds;
}
