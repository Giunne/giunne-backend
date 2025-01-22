package com.giunne.memberservice.domain.member.domain.type;

import com.giunne.memberservice.domain.auth.domain.SHA256;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 패스워드
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    private String password;

    private Password(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password must not be null or empty");
        }
        this.password = password;
    }

    public static Password createEncryptedPassword(String password) {
        return new Password(SHA256.encrypt(password));
    }

    public static Password createPassword(String encryptedPassword) {
        return new Password(encryptedPassword);
    }

    public boolean matchPassword(String password) {
        return this.password.equals(SHA256.encrypt(password));
    }
}