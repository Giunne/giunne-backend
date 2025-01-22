package com.giunne.memberservice.domain.profile.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileUrl {

    @Column(name = "url")
    private String value;

    private FileUrl(final String value) {
        this.value = value;
    }

    public static FileUrl from(final String value) {
        return new FileUrl(value);
    }

    private void setValue(String value) {this.value = value;}
    public void withdrawUrl() {
        this.setValue("탈퇴한 회원입니다.");
    }
}
