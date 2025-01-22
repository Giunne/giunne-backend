package com.giunne.memberservice.domain.member.domain.type;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * 생년월일
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Birth {

    private static final Pattern PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    @Column(name = "birth")
    private LocalDate birth;

    private Birth(LocalDate value) {
        this.birth = value;
    }

    public static Birth from(final String value) {
        validate(value);
        return new Birth(Birth.convertToLocalDate(value));
    }

    private static void validate(final String birth) {
        if (!PATTERN.matcher(birth).matches()) {
            throw new BusinessException(ErrorCode.INVALID_BIRTH_FORMAT);
        }
    }

    private static LocalDate convertToLocalDate(final String value) {
        return LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
    }

}
