package com.giunne.memberservice.domain.push.repository.entity;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.memberservice.domain.member.repository.entity.MemberEntity;
import com.giunne.memberservice.domain.push.domain.FcmToken;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name="fcm_token")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FcmTokenEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_token_no")
    private Long id;
    private Long memberId;
    private String token;

    public FcmTokenEntity(FcmToken fcmToken) {
        this.id = fcmToken.getId();
        this.memberId = fcmToken.getMemberId();
        this.token = fcmToken.getToken();
    }

    public FcmToken toFcmToken() {
       return FcmToken.builder()
               .id(id)
               .memberId(memberId)
               .token(token)
               .build()
               ;
    }
}
