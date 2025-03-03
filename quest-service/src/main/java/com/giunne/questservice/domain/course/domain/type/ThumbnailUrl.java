package com.giunne.questservice.domain.course.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 파일 URL
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThumbnailUrl {

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    private ThumbnailUrl(final String value) {
        this.thumbnailUrl = value;
    }

    public static ThumbnailUrl from(final String value) {
        return new ThumbnailUrl(value);
    }

    private void setValue(String value) {this.thumbnailUrl = value;}
}
