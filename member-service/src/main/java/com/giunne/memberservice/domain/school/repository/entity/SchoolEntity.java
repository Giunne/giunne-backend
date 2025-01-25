package com.giunne.memberservice.domain.school.repository.entity;


import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.memberservice.domain.school.domain.School;
import com.giunne.memberservice.domain.school.domain.constant.*;
import com.giunne.memberservice.domain.school.domain.constant.SchoolSe;
import com.giunne.memberservice.domain.school.domain.type.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "school")
public class SchoolEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_no")
    private Long id; // 학교번호

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "schoolId", column = @Column(name = "school_id"))
    )
    private SchoolId schoolId; // 학교ID

    @Embedded
    private SchoolNm schoolNm; // 학교명

    @Enumerated(EnumType.STRING)
    private LctnSc lctnSc; // 시도명

    @Enumerated(EnumType.STRING)
    private SchoolSe schoolSe;   // 학교급구분

    @Embedded
    private FondDate fondDate; // 설립일자

    @Enumerated(EnumType.STRING)
    private FondType fondType; // 설립형태

    @Enumerated(EnumType.STRING)
    private BnhhSe bnhhSe; // 본교분교구분

    @Embedded
    private Address address; // 주소

    @Enumerated(EnumType.STRING)
    private CddcCode cddcCode; // 시도교육청코드

    @Embedded
    private CreatDate creatDate; // 생성일자

    @Embedded
    private ChangeDate changeDate; // 수정일자

    @Embedded
    private Position position; // 위치(경도, 위도)

    @Embedded
    private ReferenceDate referenceDate; // 데이터기준일자


    public SchoolEntity(School school) {
        this.id = school.getId();
        this.schoolId = school.getSchoolId();
        this.schoolNm = school.getSchoolNm();
        this.schoolSe = school.getSchoolSe();
        this.fondDate = school.getFondDate();
        this.fondType = school.getFondType();
        this.bnhhSe = school.getBnhhSe();
        this.address = school.getAddress();
        this.cddcCode = school.getCddcCode();
        this.creatDate = school.getCreatDate();
        this.changeDate = school.getChangeDate();
        this.position = school.getPosition();
        this.referenceDate = school.getReferenceDate();
    }

    public School toSchool() {
        return School.builder()
                .id(id)
                .schoolId(schoolId)
                .schoolNm(schoolNm)
                .schoolSe(schoolSe)
                .fondDate(fondDate)
                .bnhhSe(bnhhSe)
                .address(address)
                .cddcCode(cddcCode)
                .creatDate(creatDate)
                .changeDate(changeDate)
                .position(position)
                .referenceDate(referenceDate)
                .build();
    }
}

