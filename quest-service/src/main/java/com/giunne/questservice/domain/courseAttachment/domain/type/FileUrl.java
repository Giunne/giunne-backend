package com.giunne.questservice.domain.courseAttachment.domain.type;

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
public class FileUrl {

    @Column(name = "file_url")
    private String value;

    private FileUrl(final String value) {
        this.value = value;
    }

    public static FileUrl from(final String value) {
        return new FileUrl(value);
    }

    private void setValue(String value) {this.value = value;}
}
