package com.giunne.commonservice.domain.common;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pageable {
    @Parameter(
            description = "페이지 번호",
            example = "1"
    )
    @NotNull
    private int pageIndex;
    @Parameter(
            description = "페이지 사이즈(한 페이지당 데이터 수)",
            example = "10"
    )
    @Positive
    private int pageSize;
    @Parameter(
            description = "정렬할 데이터",
            example = "create_time"
    )
    private String sortProperty;
    @Parameter(
            description = "정렬방식",
            example = "DESC"
    )
    private SortDirection sortDirection;

    public Pageable() {
        this.pageIndex = 1;
        this.pageSize = 10;
        this.sortProperty = "create_time";
        this.sortDirection = SortDirection.DESC;
    }

    public int getOffset() {
        return (pageIndex - 1) * pageSize;
    }

    public int getLimit() {
        return pageSize;
    }

    public String getProperty() {
        return sortProperty;
    }

    public String getDirection() {
        return sortDirection.name();
    }
}
