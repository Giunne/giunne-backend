package com.giunne.memberservice.domain.recreation.domain;

import lombok.NoArgsConstructor;

import java.security.SecureRandom;

@NoArgsConstructor
public class RandomCodeGenerator {
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int TOKEN_LENGTH = 15;
    private static final SecureRandom random = new SecureRandom();

    public static String generateCode() {

        StringBuilder token = new StringBuilder(TOKEN_LENGTH);

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return token.toString();
    }

}
