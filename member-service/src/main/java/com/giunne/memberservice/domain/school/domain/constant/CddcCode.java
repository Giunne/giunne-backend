package com.giunne.memberservice.domain.school.domain.constant;

import com.giunne.commonservice.domain.common.EnumMapperType;
import com.giunne.commonservice.type.CommonCodeType;
import com.giunne.memberservice.domain.member.domain.constant.Gender;
import com.giunne.memberservice.domain.member.domain.exception.ErrorCode;
import com.giunne.memberservice.domain.member.domain.exception.InvalidGenderException;
import lombok.Getter;

import java.util.Arrays;

/**
 * 시도교육청코드
 * 학교를 관리하는 시도교육청 기관코드
 */

@Getter
public enum CddcCode implements EnumMapperType {

    SEOUL("7010000", "서울특별시교육청"),
    BUSAN ("7150000", "부산광역시교육청"),
    DAEGU ("7240000","대구광역시교육청" ),
    INCHEON ("7310000", "인천광역시교육청"),
    GWANGJU ("7380000", "광주광역시교육청"),
    DAEJEON ("7430000","대전광역시교육청" ),
    ULSAN ("7480000","울산광역시교육청" ),
    SEJONG ("9300000", "세종특별자치시교육청"),
    GYEONGGI ("7530000","경기도교육청" ),
    GANGWON ("7801000","강원특별자치도교육청"),
    CHUNGBUK ("8000000", "충청북도교육청"),
    CHUNGNAM ("8140000","충청남도교육청" ),
    JEONBUK ("8321000","전북특별자치도교육청"),
    JEONNAM("8490000","전라남도교육청" ),
    GYEONGBUK("8750000","경상북도교육청" ),
    GYEONGNAM("9010000", "경상남도교육청"),
    JEJU ("9290000", "제주특별자치도교육청"),
    ;

    private final String code;
    private final String codeName;

    CddcCode(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static CddcCode from(String cddcCode) {
        return Arrays.stream(values())
                .filter(item -> item.code.equals(cddcCode))
                .findFirst()
                .orElseThrow(
                        () -> new InvalidGenderException(ErrorCode.INVALID_CDDC_CODE)
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
