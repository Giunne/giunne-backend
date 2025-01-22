package com.giunne.memberservice.domain.school.domain.constant;

import com.giunne.commonservice.domain.common.EnumMapperType;
import com.giunne.commonservice.error.exception.BusinessException;
import com.giunne.commonservice.type.CommonCodeType;
import com.giunne.memberservice.domain.member.domain.constant.Gender;
import com.giunne.memberservice.domain.member.domain.exception.ErrorCode;
import com.giunne.memberservice.domain.member.domain.exception.InvalidGenderException;
import lombok.Getter;

import java.util.Arrays;

/**
 * 시도명
 * 학교가 소속되어 있는 시도교육청이 위치한 시도
 */
@Getter
public enum LctnSc implements EnumMapperType {

    SEOUL("11","서울특별시"),
    BUSAN ("21","부산광역시"),
    DAEGU ("22","대구광역시"),
    INCHEON ("23","인천광역시"),
    GWANGJU ("24","광주광역시"),
    DAEJEON ("25","대전광역시"),
    ULSAN ("26","울산광역시"),
    SEJONG ("29","세종특별자치시"),
    GYEONGGI ("31","경기도"),
    GANGWON ("23","강원특별자치도"),
    CHUNGBUK ("33","충청북도"),
    CHUNGNAM ("34","충청남도"),
    JEONBUK("35","전라북도"),
    JEONNAM("36","전라남도"),
    GYEONGBUK("39","경상북도"),
    GYEONGNAM("38","경상남도"),
    JEJU ("39","제주특별자치도"),
    OVERSEAS ("99","재외한국학교")
    ;

    private final String code;
    private final String codeName;

    LctnSc(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static LctnSc from(String lctnScCode) {
        return Arrays.stream(values())
                .filter(item -> item.code.equals(lctnScCode))
                .findFirst()
                .orElseThrow(
                        () -> new BusinessException(ErrorCode.INVALID_LCTNSC)
                );
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTitle() {
        return codeName;
    }

}
