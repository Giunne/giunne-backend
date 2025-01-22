package com.giunne.memberservice.domain.recreation.domain;

import com.giunne.commonservice.domain.common.BaseEntity;
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

//    // 양방향 매핑
//    @OneToMany(mappedBy = "recreation", orphanRemoval = true, cascade = CascadeType.ALL)
//    private Set<RecreationMember> members = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_no")
    private SchoolEntity school; // 학교정보
}
