package com.giunne.memberservice.domain.recreationMember.domain;

import com.giunne.memberservice.domain.member.repository.entity.MemberEntity;
import com.giunne.memberservice.domain.recreation.domain.RecreationEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recreation_member")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecreationMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recreation_member_no")
    private Long id; // 레크레이션_회원 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private MemberEntity member; // 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recreation_no")
    private RecreationEntity recreation; // 레크레이션
}
