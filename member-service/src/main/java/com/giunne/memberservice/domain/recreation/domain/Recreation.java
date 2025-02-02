package com.giunne.memberservice.domain.recreation.domain;

import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.recreation.domain.type.BaseNumber;
import com.giunne.memberservice.domain.recreation.domain.type.RecreationCode;
import com.giunne.memberservice.domain.recreation.domain.type.RecreationName;
import com.giunne.memberservice.domain.school.domain.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Recreation {
    private Long id; // 레크레이션 번호
    private RecreationName recreationName; // 레크레이션명
    private RecreationCode recreationCode; // 레크레이션 코드
    private School school; // 학교
    private Member member; // 회원
    private BaseNumber baseNumber; // 기수번호
}
