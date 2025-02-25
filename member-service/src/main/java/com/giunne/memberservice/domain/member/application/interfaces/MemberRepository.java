package com.giunne.memberservice.domain.member.application.interfaces;

import com.giunne.memberservice.domain.member.domain.Member;

public interface MemberRepository {
    Member save(Member member);
    Member findById(Long id);
    Member findByLoginId(String loginId);
    void validateDuplicateMember(String loginId);
    void passwordChange(String loginId, String password);
}
