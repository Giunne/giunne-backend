package com.giunne.memberservice.domain.school.domain.constant;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 시도교육청명
// 학교를 관리하는 시도교육청 명칭
@Getter
public enum AtptOfcdcScName {
    SEOUL( "서울특별시교육청"),
    BUSAN ( "부산광역시교육청"),
    DAEGU ( "대구광역시교육청"),
    INCHEON ( "인천광역시교육청"),
    GWANGJU ( "광주광역시교육청"),
    DAEJEON ( "대전광역시교육청"),
    ULSAN ( "울산광역시교육청"),
    SEJONG ( "세종특별자치시교육청"),
    GYEONGGI ( "경기도교육청"),
    GANGWON ( "강원특별자치도교육청"),
    CHUNGBUK ( "충청북도교육청"),
    CHUNGNAM ( "충청남도교육청"),
    JEONBUK ( "전북특별자치도교육청"),
    JEONNAM( "전라남도교육청"),
    GYEONGBUK( "경상북도교육청"),
    GYEONGNAM( "경상남도교육청"),
    JEJU ( "제주특별자치도교육청"),
    OVERSEAS ( "재외교육지원담당관실")
        ;

    private String description;

    AtptOfcdcScName(String description) {
        this.description = description;
    }

    public static AtptOfcdcScName from(String atptOfcdcScName) {
        validate(atptOfcdcScName);
        return AtptOfcdcScName.valueOf(atptOfcdcScName.toUpperCase());
    }

    public static boolean isAtptOfcdcScName(String atptOfcdcScName) {
        List<AtptOfcdcScName> atptOfcdcScNames = Arrays.stream(AtptOfcdcScName.values())
                .filter(g -> g.name().equals(atptOfcdcScName))
                .collect(Collectors.toList());

        return atptOfcdcScNames.size() != 0;
    }

    private static void validate(String atptOfcdcScName) {
        if(!AtptOfcdcScName.isAtptOfcdcScName(atptOfcdcScName.toUpperCase())) {
            throw new BusinessException(ErrorCode.INVALID_ATPT_OFCDC_SC_CODE_NAME);
        }
    }
}
