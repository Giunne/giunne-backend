package com.giunne.itemservice.domain.item.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 미리보기 이미지 URL
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThumbnailUrl {

    @Column(name = "thumbnail_url")
    private String value;

    private ThumbnailUrl(final String value) {
        this.value = value;
    }

    public static ThumbnailUrl from(final String value) {
        return new ThumbnailUrl(value);
    }

    private void setValue(String value) {this.value = value;}
}
