package com.giunne.itemservice.domain.itemImage.domain.type;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 파일 사이즈
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileSize {

    @Column(name = "file_size", nullable = false)
    private Long value;

    private FileSize(final Long value) {
        this.value = value;
    }

    public static FileSize from(final Long value) {
        validate(value);
        return new FileSize(value);
    }

    private void setValue(Long value) {this.value = value;}

    private static void validate(final Long value) {
        if (value > 1000000) {
            throw new BusinessException(ErrorCode.PROFILE_SIZE_LIMIT);
        }
    }
}

