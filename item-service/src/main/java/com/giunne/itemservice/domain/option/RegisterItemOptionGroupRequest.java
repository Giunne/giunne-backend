package com.giunne.itemservice.domain.option;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class RegisterItemOptionGroupRequest {
    @NotNull(message = "ordering 을 작성하세요.")
    private Integer ordering;
    @NotBlank(message = "상품 옵션 그룹 이름을 작성하세요.")
    private String name;
    private List<RegisterItemOptionRequest> itemOptions;
}
