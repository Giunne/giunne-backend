package com.giunne.commonservice.ui;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationModel<T>  {
    @Schema(description = "페이지 데이터")
    List<T> data;
    private PaginationInfo PaginationInfo;
}
