package com.giunne.memberservice.domain.school.api.request;

import com.giunne.memberservice.domain.school.domain.School;
import com.giunne.memberservice.domain.school.domain.constant.BnhhSe;
import com.giunne.memberservice.domain.school.domain.constant.CddcCode;
import com.giunne.memberservice.domain.school.domain.constant.FondType;
import com.giunne.memberservice.domain.school.domain.constant.SchoolSe;
import com.giunne.memberservice.domain.school.domain.type.*;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolCsvDto {

    @CsvBindByName(column = "schoolId2")
    private String schoolId2;
    @CsvBindByName(column = "schoolId")
    private String schoolId;
    @CsvBindByName(column = "schoolNm")
    private String schoolNm;
    @CsvBindByName(column = "schoolSe")
    private String schoolSe;
    @CsvBindByName(column = "fondDate")
    private String fondDate;
    @CsvBindByName(column = "fondType")
    private String fondType;
    @CsvBindByName(column = "bnhhSe")
    private String bnhhSe;
    @CsvBindByName(column = "operSttus")
    private String operSttus;
    @CsvBindByName(column = "lnmadr")
    private String lnmadr;
    @CsvBindByName(column = "rdnmadr")
    private String rdnmadr;
    @CsvBindByName(column = "cddcCode")
    private String cddcCode;
    @CsvBindByName(column = "cddcNm")
    private String cddcNm;
    @CsvBindByName(column = "edcSport")
    private String edcSport;
    @CsvBindByName(column = "edcSportNm")
    private String edcSportNm;
    @CsvBindByName(column = "creatDate")
    private String creatDate;
    @CsvBindByName(column = "changeDate")
    private String changeDate;
    @CsvBindByName(column = "latitude")
    private double latitude;
    @CsvBindByName(column = "longitude")
    private double longitude;
    @CsvBindByName(column = "referenceDate")
    private String referenceDate;
    @CsvBindByName(column = "abc")
    private String abc;

    public School toSchool(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return School.builder()
                .schoolId(SchoolId.from(this.getSchoolId()))
                .schoolNm(SchoolNm.from(this.getSchoolNm()))
                .schoolSe(SchoolSe.toSchoolSe(this.getSchoolSe()))
                .fondDate(FondDate.from(LocalDate.parse(this.getFondDate(), formatter).atStartOfDay()))
                .fondType(FondType.toFondType(this.getFondType()))
                .bnhhSe(BnhhSe.toBnhhSe(this.getBnhhSe()))
                .address(
                        Address.builder()
                                .inmadr(Inmadr.from(this.getLnmadr()))
                                .rdnmadr(Rdnmadr.from(this.getRdnmadr()))
                                .build()
                )
                .cddcCode(CddcCode.from(this.getCddcCode()))
                .creatDate(CreatDate.from(LocalDate.parse(this.getCreatDate(), formatter).atStartOfDay()))
                .changeDate(ChangeDate.from(LocalDate.parse(this.getChangeDate(), formatter).atStartOfDay()))
                .position(Position.of(this.getLatitude(), this.getLongitude()))
                .referenceDate(ReferenceDate.from(LocalDate.parse(this.getReferenceDate(), formatter).atStartOfDay()))
                .build();
    }

}
