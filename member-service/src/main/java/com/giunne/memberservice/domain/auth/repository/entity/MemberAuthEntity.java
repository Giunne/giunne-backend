package com.giunne.memberservice.domain.auth.repository.entity;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.memberservice.domain.auth.domain.MemberAuth;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Entity
@Table(name = "community_user_auth")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberAuthEntity extends BaseEntity {
    @Id
    private String loginId;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberRole role;
    private Long memberId;
    private LocalDateTime lastLoginDt;

    @Builder
    public MemberAuthEntity(MemberAuth memberAuth, Long memberId) {
        this.loginId = memberAuth.getLoginId();
        this.password = memberAuth.getPassword();
        this.role = memberAuth.getRole();
        this.memberId = memberId;
    }

    public MemberAuth toMemberAuth() {
        return MemberAuth.builder()
                .loginId(loginId)
                .password(password)
                .role(role)
                .memberId(memberId)
                .build();
    }

    public void updateLastLoginAt() {
        this.lastLoginDt = LocalDateTime.now();
    }

}
