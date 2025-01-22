package com.giunne.itemservice.domain.Profile.domain;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.itemservice.domain.ProfilePosition.domain.ProfilePosition;
import com.giunne.itemservice.domain.Profile.domain.type.*;
import com.giunne.itemservice.domain.item.domain.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_no")
    private Long id; // 프로필 번호

    @Embedded
    private FileName fileName; // 파일명

    @Embedded
    private FileUrl fileUrl; // 파일 URL

    @Embedded
    private FileSize fileSize; // 파일 사이즈

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_no")
    private Item item; // 아이템
    
    // 양방향 매핑
    @OneToMany(mappedBy = "profile", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ProfilePosition> profilePositions = new HashSet<>(); // 프로필 위치

    @Embedded
    private Active isActive = Active.from(true);

    @Embedded
    private IsRepresent isRepresent = IsRepresent.from(true);

    @Builder
    public Profile(FileName fileName, FileUrl fileUrl, FileSize fileSize, Active isActive, IsRepresent isRepresent) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
        this.isActive = isActive;
        this.isRepresent = isRepresent;
    }

    public void updateProfile(FileUrl fileUrl, FileSize fileSize) {
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
    }

}
