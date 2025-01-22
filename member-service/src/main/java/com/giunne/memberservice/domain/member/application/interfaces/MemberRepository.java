package com.giunne.memberservice.domain.member.application.interfaces;

import com.giunne.memberservice.domain.member.domain.Member;

public interface MemberRepository {
    Member save(Member member);
    Member findById(Long id);
    void validateDuplicateMember(String loginId);
}
