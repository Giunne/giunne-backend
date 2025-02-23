package com.giunne.memberservice.domain.avatar.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;

import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.avatar.domain.type.*;
import com.giunne.memberservice.domain.member.repository.entity.MemberEntity;
import com.giunne.memberservice.domain.recreation.repository.entity.RecreationEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "avatar")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvatarEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_no")
    private Long id; // 캐릭터 번호
    
    @Embedded
    private Nickname nickname; // 닉네임
    
    @Embedded
    private BaseNumber baseNumber; // 기수번호

    @Embedded
    private ClassRoom classRoom; // 교실

    @Embedded
    private Exp exp = Exp.from(1L); // 경험치

    @Embedded
    private Level level = Level.from(1L); // 레벨

    @Embedded
    private Point point = Point.from(0L); // 포인트

    @Column(name = "character_no")
    private Long characterNo; // 캐릭터 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recreation_no")
    private RecreationEntity recreation; // 레크레이션

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private MemberEntity member; // 회원

    @Embedded
    private Active isActive = Active.from(true);


    public AvatarEntity(Avatar avatar){
        this.id = avatar.getId();
        this.nickname = avatar.getNickname();
        this.baseNumber = avatar.getBaseNumber();
        this.classRoom = avatar.getClassRoom();
        this.exp = avatar.getExp();
        this.level = avatar.getLevel();
        this.point = avatar.getPoint();
        this.characterNo = avatar.getCharacterNo();
        this.member = new MemberEntity(avatar.getMember());
        this.recreation = new RecreationEntity(avatar.getRecreation());
    }

    public Avatar toAvatar(){
        return Avatar.builder()
                .id(id)
                .nickname(nickname)
                .baseNumber(baseNumber)
                .classRoom(classRoom)
                .exp(exp)
                .level(level)
                .point(point)
                .characterNo(characterNo)
                .member(member.toMember())
                .recreation(recreation.toRecreation())
                .build();
    }

}
