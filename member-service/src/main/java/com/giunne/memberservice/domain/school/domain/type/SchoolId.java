package com.giunne.memberservice.domain.school.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 전국초중등학교위치표준데이터 학교ID
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolId {

    @Column(name = "school_id", nullable = false)
    private String schoolId;

    public SchoolId(final String value) {
        this.schoolId = value;
    }

    public static SchoolId from(final String value) {
        return new SchoolId(value);
    }

    private void setValue(String value) {this.schoolId = value;}
}
