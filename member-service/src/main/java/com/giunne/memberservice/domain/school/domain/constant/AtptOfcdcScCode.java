package com.giunne.memberservice.domain.school.domain.constant;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 시도교육청코드
// 학교를 관리하는 시도교육청 기관코드
@Getter
public enum AtptOfcdcScCode {

    B10("B10"),
    C10("C10"),
    D10("D10"),
    E10("E10"),
    F10("F10"),
    G10("G10"),
    H10("H10"),
    I10("I10"),
    J10("J10"),
    K10("K10"),
    M10("M10"),
    N10("N10"),
    P10("P10"),
    Q10("Q10"),
    R10("R10"),
    S10("S10"),
    T10("T10"),
    V10("V10");

    private final String description; // 시도교육청코드: 학교를 관리하는 시도교육청 기관코드

    AtptOfcdcScCode(String description) {
        this.description = description;
    }
    public static AtptOfcdcScCode from(String description) {
        validate(description);
        return AtptOfcdcScCode.valueOf(description.toUpperCase());
    }

    public static boolean isAtptOfcdcScCode(String atptOfcdcScCode) {
        List<AtptOfcdcScCode> atptOfcdcScCodes = Arrays.stream(AtptOfcdcScCode.values())
                .filter(g -> g.name().equals(atptOfcdcScCode))
                .collect(Collectors.toList());

        return atptOfcdcScCodes.size() != 0;
    }

    private static void validate(String atptOfcdcScName) {
        if(!AtptOfcdcScCode.isAtptOfcdcScCode(atptOfcdcScName.toUpperCase())) {
            throw new BusinessException(ErrorCode.INVALID_ATPT_OFCDC_SC_CODE);
        }
    }

}

