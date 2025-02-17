package com.giunne.questservice.domain.station.domain.domain;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.quest.api.domain.Quest;
import com.giunne.questservice.domain.station.domain.domain.type.StationName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "station")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Station extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_no")
    private Long id; // 클리어 로그 번호

    @Embedded
    private StationName stationName; //역 이름

    @OneToMany(mappedBy = "station", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Quest> quests = new HashSet<>();
}
