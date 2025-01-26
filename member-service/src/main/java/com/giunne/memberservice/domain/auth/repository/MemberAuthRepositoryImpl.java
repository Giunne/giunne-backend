package com.giunne.memberservice.domain.auth.repository;

import com.giunne.memberservice.domain.auth.application.interfaces.MemberAuthRepository;
import com.giunne.memberservice.domain.auth.domain.MemberAuth;
import com.giunne.memberservice.domain.auth.repository.entity.MemberAuthEntity;
import com.giunne.memberservice.domain.auth.repository.jpa.JpaMemberAuthRepository;
import com.giunne.memberservice.domain.member.application.interfaces.MemberRepository;
import com.giunne.memberservice.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberAuthRepositoryImpl implements MemberAuthRepository {

    private final JpaMemberAuthRepository jpaMemberAuthRepository;
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public MemberAuth registerMember(MemberAuth memberAuth, Member member) {
        Member saveMember = memberRepository.save(member);
        MemberAuthEntity memberAuthEntity = MemberAuthEntity.builder()
                .memberAuth(memberAuth)
                .memberId(saveMember.getId())
                .build();
        MemberAuthEntity saveMemberAuthEntity = jpaMemberAuthRepository.save(memberAuthEntity);

        return saveMemberAuthEntity.toMemberAuth();
    }

    @Override
    @Transactional
    public MemberAuth loginMember(String loginId, String password) {
        MemberAuthEntity memberAuthEntity = jpaMemberAuthRepository.findByLoginId(loginId).orElseThrow();

        MemberAuth memberAuth = memberAuthEntity.toMemberAuth();

        if (!memberAuth.matchPassword(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        memberAuthEntity.updateLastLoginAt();
        return memberAuth;
    }
}
