package com.giunne.itemservice.domain.ProfilePosition.domain;

import com.giunne.itemservice.domain.Profile.domain.Profile;
import com.giunne.itemservice.domain.ProfilePosition.domain.type.Position;
import com.giunne.itemservice.domain.item.domain.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profile_position")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfilePosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 아이템 프로필 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_no")
    private Profile profile; // 프로필

    @Embedded
    private Position position; // 위치

    @Column(name = "item_no")
    private Long characterNo; // 캐릭터 번호
}
