package com.giunne.memberservice.domain.recreation.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.memberservice.domain.member.repository.entity.MemberEntity;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import com.giunne.memberservice.domain.recreation.domain.type.BaseNumber;
import com.giunne.memberservice.domain.recreation.domain.type.RecreationCode;
import com.giunne.memberservice.domain.recreation.domain.type.RecreationName;
import com.giunne.memberservice.domain.school.repository.entity.SchoolEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recreation")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecreationEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recreation_no")
    private Long id; // 레크레이션 번호

    @Embedded
    private RecreationName recreationName; // 레크레이션명

    @Embedded
    private RecreationCode recreationCode; // 레크레이션 코드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_no")
    private SchoolEntity school; // 학교정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_no")
    private MemberEntity teacher; // 선생님

    @Embedded
    private BaseNumber baseNumber; // 기수번호

    @Embedded
    private Active isActive = Active.from(true);

    public RecreationEntity(Recreation recreation) {
        this.id = recreation.getId();
        this.recreationName = recreation.getRecreationName();
        this.recreationCode = recreation.getRecreationCode();
        this.baseNumber = recreation.getBaseNumber();
        this.school = new SchoolEntity(recreation.getSchool());
        this.teacher = new MemberEntity(recreation.getMember());
    }

    public Recreation toRecreation() {
        return Recreation.builder()
                .id(this.id)
                .recreationName(this.recreationName)
                .recreationCode(this.recreationCode)
                .baseNumber(baseNumber)
                .school(this.school.toSchool())
                .member(this.teacher.toMember())
                .build();
    }

}
