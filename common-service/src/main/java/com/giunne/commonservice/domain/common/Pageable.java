package com.giunne.commonservice.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pageable {
    private int pageIndex;
    private int pageSize;
    private String sortProperty;
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
