package com.giunne.commonservice.infra.external.domain.item.client.dto.request;

import com.giunne.commonservice.domain.common.Pageable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "아이템 리스트 검색 요청DTO")
public class GetItemsRequestDto extends Pageable {
    List<Long> itemIds;
    Long categoryId;
}
