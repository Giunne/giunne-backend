package com.giunne.itemservice.domain.Profile.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 파일명
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileName {

    @Column(name = "file_name")
    private String value;

    private FileName(final String value) {
        this.value = value;
    }

    public static FileName from(final String value) {
        return new FileName(value);
    }

    private void setValue(String value) {this.value = value;}
}

