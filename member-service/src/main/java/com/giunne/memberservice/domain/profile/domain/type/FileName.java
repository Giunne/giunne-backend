package com.giunne.memberservice.domain.profile.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileName {

    @Column(name = "origin_name")
    private String value;

    private FileName(final String value) {
        this.value = value;
    }

    public static FileName from(final String value) {
        return new FileName(value);
    }

    private void setValue(String value) {this.value = value;}
    public void withdrawName() {
        this.setValue("탈퇴한 회원입니다.");
    }
}
