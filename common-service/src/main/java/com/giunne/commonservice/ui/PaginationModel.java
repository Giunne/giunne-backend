package com.giunne.commonservice.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationModel<T>  {
    List<T> data;
    private PaginationInfo PaginationInfo;
}
