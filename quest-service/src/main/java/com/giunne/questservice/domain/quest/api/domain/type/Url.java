package com.giunne.questservice.domain.quest.api.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유튜브 URL
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Column(name = "file_url")
    private String value;

    private Url(final String value) {
        this.value = value;
    }

    public static Url from(final String value) {
        return new Url(value);
    }

    private void setValue(String value) {this.value = value;}
}

