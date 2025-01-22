package com.giunne.commonservice.util;

import com.giunne.commonservice.ui.PaginationInfo;
import com.giunne.commonservice.ui.PaginationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PaginationUtil {

    public static <T> PaginationModel<T> toPaginationModel(Page<T> page) {
        List<T> data = page.getContent();

        PaginationInfo pagination = new PaginationInfo(
                page.getNumber() + 1,
                page.getTotalPages(),
                page.getSize(),
                page.getTotalElements(),
                page.hasNext(),
                page.hasPrevious());

        return new PaginationModel<>(data, pagination);
    }

    public static PageRequest getPageRequest(int page, int size, Sort sort) {
        return PageRequest.of(page - 1
                , size
                , sort != null ? sort : Sort.unsorted());
    }
}
