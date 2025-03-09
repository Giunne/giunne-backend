package com.giunne.questservice.domain.quest.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 가이드 URL
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuideUrl {

    @Column(name = "guide_url", nullable = false)
    private String guideUrl;

    private GuideUrl(final String value) {
        this.guideUrl = value;
    }

    public static GuideUrl from(final String value) {
        return new GuideUrl(value);
    }

    private void setValue(String value) {this.guideUrl = value;}
}
