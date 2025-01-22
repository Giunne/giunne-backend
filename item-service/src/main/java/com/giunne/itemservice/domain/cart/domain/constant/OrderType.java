package com.giunne.itemservice.domain.cart.domain.constant;

import com.giunne.commonservice.type.CommonCodeType;

/**
 * 주문 타입
 */

public enum OrderType implements CommonCodeType {
    BUY("001", "구매"),
    SELL("002", "판매"),
    ;

    private final String code;
    private final String codeName;

    OrderType(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.codeName;
    }
}
