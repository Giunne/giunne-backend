package com.giunne.memberservice.domain.profile.domain;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.memberservice.domain.member.repository.entity.MemberEntity;
import com.giunne.memberservice.domain.profile.domain.type.FileName;
import com.giunne.memberservice.domain.profile.domain.type.FileSize;
import com.giunne.memberservice.domain.profile.domain.type.FileUrl;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 프로필 번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private MemberEntity member;

    @Embedded
    private FileName fileName;

    @Embedded
    private FileUrl fileUrl;

    @Embedded
    private FileSize fileSize;

    @Embedded
    private Active isActive = Active.from(true);

    @Builder
    public Profile(MemberEntity member, FileName fileName, FileUrl fileUrl, FileSize fileSize, Active isActive) {
        this.member = member;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
        this.isActive = isActive;
    }

    public void updateMember(MemberEntity member) {
        setMember(member);
    }

    private void setMember(MemberEntity member) {
        this.member = member;
    }

    public void updateProfile(FileUrl fileUrl, FileSize fileSize) {
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
    }

    public void withdrawProfile() {
        this.fileName.withdrawName();
        this.fileUrl.withdrawUrl();
        this.fileSize.withdrawSize();
    }
}
