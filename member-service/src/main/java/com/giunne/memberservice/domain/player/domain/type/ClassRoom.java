package com.giunne.memberservice.domain.player.domain.type;

import com.giunne.memberservice.domain.member.domain.type.Grade;
import com.giunne.memberservice.domain.member.domain.type.StudentNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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
