package com.giunne.itemservice.domain.option;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public class RegisterItemOptionRequest {
    @NotBlank(message = "상품 옵션 이름을 작성하세요.")
    private String name;
    @NotNull(message = "ordering 을 작성하세요.")
    private Integer ordering;
    @NotNull(message = "상품 옵션 가격을 입력하세요.")
    @Range(min = 0, max = 200000000, message = "상품 옵션 가격은 0 ~ 2억 미만이여야 합니다.")
    private Integer price;
}
