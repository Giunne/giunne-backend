package com.giunne.memberservice.domain.school.domain;

import com.giunne.memberservice.domain.school.api.request.SchoolCsvDto;
import com.giunne.memberservice.domain.school.domain.constant.*;
import com.giunne.memberservice.domain.school.domain.constant.SchoolSe;
import com.giunne.memberservice.domain.school.domain.type.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
public class School {
    private Long id; // 학교번호
    private SchoolId schoolId; // 학교ID
    private SchoolNm schoolNm; // 학교명
    private FondDate fondDate; // 설립일자
    private LctnSc lctnSc; // 시도명
    private SchoolSe schoolSe;   // 학교급구분
    private JuOrgNm juOrgNm; // 관할조직명
    private FondType fondType; // 설립형태
    private BnhhSe bnhhSe; // 본교분교구분
    private Address address; // 주소
    private CddcCode cddcCode; // 시도교육청코드
    private CreatDate creatDate; // 생성일자
    private ChangeDate changeDate; // 수정일자
    private Position position; // 위치(경도, 위도)
    private ReferenceDate referenceDate; // 데이터기준일자
}
