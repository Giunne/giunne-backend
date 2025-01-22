package com.giunne.commonservice.ui;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PaginationInfo {
    private Integer currentPage;
    private Integer totalPage;
    private Integer pageSize;
    private Long totalCount;
    private Boolean hasNextPage;
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
