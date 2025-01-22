package com.giunne.itemservice.domain.item.domain.constant;

import com.giunne.commonservice.type.CommonCodeType;

/**
 * 캐릭터 타입
 */

public enum ItemType implements CommonCodeType {

    PENGUIN("PG-001", "펭귄"),
    CAT("C-002", "고양이"),
    DINOSAUR("DNS-003", "공룡"),
    RACCOON("RC-004", "너구리"),
    TIGER("TG-005", "호랑이"),
    RABBIT("RB-006", "토끼"),
    POODLE("PD-007", "푸들"),
    DUCK("D-008", "오리"),
    ;

    private final String code;
    private final String codeName;

    ItemType(String code, String codeName) {
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
