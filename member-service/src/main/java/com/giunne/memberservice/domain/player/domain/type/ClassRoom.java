package com.giunne.memberservice.domain.player.domain.type;

import com.giunne.memberservice.domain.member.domain.type.Grade;
import com.giunne.memberservice.domain.member.domain.type.StudentNumber;
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

    @Embedded
    private Grade gradeNo;

    @Embedded
    private ClassNumber classNo;

    @Embedded
    private StudentNumber studentNumber;

    @Builder
    public ClassRoom(Grade gradeNo, ClassNumber classNo, StudentNumber studentNumber) {
        this.gradeNo = gradeNo;
        this.classNo = classNo;
        this.studentNumber = studentNumber;
    }
}
