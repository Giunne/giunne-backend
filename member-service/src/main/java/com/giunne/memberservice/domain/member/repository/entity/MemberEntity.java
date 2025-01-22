package com.giunne.memberservice.domain.member.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.commonservice.domain.auth.OAuthType;
import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.member.domain.constant.Gender;
import com.giunne.memberservice.domain.member.domain.type.*;
import com.giunne.memberservice.domain.school.repository.entity.SchoolEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private Long id; // 회원 번호

    @Embedded
    @AttributeOverride(name = "loginId", column = @Column(name = "login_id"))
    private LoginId loginId; // 로그인 ID

    @Embedded
    @AttributeOverride(name = "password", column = @Column(name = "password"))
    private Password password; // 로그임 패스워드

    @Embedded
    @AttributeOverride(name = "userName", column = @Column(name = "username"))
    private UserName userName; // 회원명

    @Embedded
    @AttributeOverride(name = "nickname", column = @Column(name = "nickname"))
    private Nickname nickname; // 닉네임

    @Embedded
    @AttributeOverride(name = "birth", column = @Column(name = "birth"))
    private Birth birth; // 생년월일

    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별 구분

    @Embedded
    private Phone phone; // 휴대폰 번호

    @Embedded
    private Email email; // 이메일

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberRole role = MemberRole.ROLE_STUDENT;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_no")
    private SchoolEntity school; // 학교정보

    @Column(name = "refresh_token", length = 500)
    private String refreshToken; // 리프레시토큰

    @Column(name = "refresh_token_expiration_time")
    private LocalDateTime refreshTokenExpirationTime; // 리프레시토큰 만료날짜

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_type", nullable = false)
    private OAuthType oAuthType; // OAuth 구분

    @Embedded
    private Active isActive = Active.from(true);

//    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Profile profile; // 프로필정보
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private Set<Player> players; // 캐릭터
//
//    // 양방향 매핑
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private Set<RecreationMember> recreations = new HashSet<>();

    public MemberEntity(Member member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
        this.userName = member.getUserName();
        this.nickname = member.getNickname();
        this.birth = member.getBirth();
        this.gender = member.getGender();
        this.phone = member.getPhone();
        this.email = member.getEmail();
        this.role = member.getRole();
        this.school = new SchoolEntity(member.getSchool());
        this.refreshToken = member.getRefreshToken();
        this.refreshTokenExpirationTime = member.getRefreshTokenExpirationTime();
        this.oAuthType = member.getOAuthType();
    }

    public Member toMember() {
        return Member.builder()
                .id(id)
                .loginId(loginId)
                .password(password)
                .userName(userName)
                .nickname(nickname)
                .birth(birth)
                .gender(gender)
                .phone(phone)
                .email(email)
                .role(role)
                .school(school.toSchool())
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(refreshTokenExpirationTime)
                .oAuthType(oAuthType)
                .build();
    }

}
