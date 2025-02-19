package com.giunne.commonservice.ui;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "페이지 메타데이터")
public class PaginationInfo {
    @Schema( description = "현재 페이지 번호",
            example = "1")
    private Integer currentPage;
    @Schema(description = "현재 페이지 크기",
            example = "100")
    private Integer totalPage;
    @Schema(description = "페이지 사이즈(한 페이지당 데이터 수)",
            example = "10")
    private Integer pageSize;
    @Schema(description = "전체 데이터 수",
            example = "1000")
    private Long totalCount;
    @Schema(description = "다음 페이지 유무",
            example = "true")
    private Boolean hasNextPage;
    @Schema(description = "이전 페이지 유무",
            example = "false")
    private Boolean hasPreviousPage;

    @Builder
    public PaginationInfo(Integer currentPage, Integer totalPage, Integer pageSize, Long totalCount, Boolean hasNextPage, Boolean hasPreviousPage) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.hasNextPage = hasNextPage;
        this.hasPreviousPage = hasPreviousPage;
    }
}
