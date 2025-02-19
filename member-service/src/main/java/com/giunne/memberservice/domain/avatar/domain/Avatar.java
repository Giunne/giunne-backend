package com.giunne.memberservice.domain.avatar.domain;

import com.giunne.memberservice.domain.avatar.domain.type.*;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Avatar {
    private Long id; // 캐릭터 번호
    private Nickname nickname; // 닉네임
    private BaseNumber baseNumber; // 기수번호
    private ClassRoom classRoom; // 교실
    private Exp exp; // 경험치
    private Level level; // 레벨
    private Point point; // 포인트
    private Long characterNo; // 캐릭터 번호
    private Member member; //회원
    private Recreation recreation; //레크레이션
}
