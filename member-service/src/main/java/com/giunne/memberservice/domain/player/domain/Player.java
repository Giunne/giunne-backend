package com.giunne.memberservice.domain.player.domain;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;

import com.giunne.memberservice.domain.player.domain.type.*;
import com.giunne.memberservice.domain.inventory.domain.Inventory;
import com.giunne.memberservice.domain.member.repository.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "player")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_no")
    private Long id; // 캐릭터 번호
    
    @Embedded
    private Nickname nickname; // 닉네임
    
    @Embedded
    private BaseNumber baseNumber; // 기수번호

    @Embedded
    private ClassRoom classRoom; // 교실

    @Embedded
    private Exp exp; // 경험치

    @Embedded
    private Level level; // 레벨

    @Embedded
    private Point point; // 포인트

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Inventory> inventories = new HashSet<>(); // 인벤토리

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private MemberEntity member; // 회원

    @Embedded
    private Active isActive = Active.from(true);
}
