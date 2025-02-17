package com.giunne.itemservice.domain.itemImage.repositoy.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.itemservice.domain.itemImage.domain.ItemImage;
import com.giunne.itemservice.domain.itemImage.domain.type.*;
import com.giunne.itemservice.domain.item.repository.entity.ItemEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "item_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_no")
    private Long id; // 프로필 번호

    @Embedded
    private FileName fileName; // 파일명

    @Embedded
    private FileUrl fileUrl; // 파일 URL

    @Embedded
    private FileSize fileSize; // 파일 사이즈

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_no")
    private ItemEntity item; // 아이템

    @Embedded
    private Active isActive = Active.from(true);

    @Embedded
    private IsRepresent isRepresent = IsRepresent.from(true);

    @Builder
    public ItemImageEntity(FileName fileName, FileUrl fileUrl, FileSize fileSize, Active isActive, IsRepresent isRepresent) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
        this.isActive = isActive;
        this.isRepresent = isRepresent;
    }

    public ItemImageEntity(ItemImage itemImage) {
        this.id = itemImage.getId();
        this.fileName = itemImage.getFileName();
        this.fileUrl = itemImage.getFileUrl();
        this.fileSize = itemImage.getFileSize();
        this.item = new ItemEntity(itemImage.getItem());
    }

    public ItemImage toItemImage(){
        return ItemImage.builder()
                .id(id)
                .fileName(fileName)
                .fileName(fileName)
                .fileSize(fileSize)
                .fileUrl(fileUrl)
                .item(item.toItem())
                .build();
    }

    public void updateProfile(FileUrl fileUrl, FileSize fileSize) {
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
    }

}
