package com.giunne.memberservice.domain.avatar.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 교실
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassRoom {

    @Column(name = "grade")
    private Integer grade;
    @Column(name = "class_number")
    private Integer classNumber;
    @Column(name = "student_number")
    private Integer studentNumber;

    @Builder
    public ClassRoom(Integer grade, Integer classNumber, Integer studentNumber) {
        this.grade = grade;
        this.classNumber = classNumber;
        this.studentNumber = studentNumber;
    }
}
