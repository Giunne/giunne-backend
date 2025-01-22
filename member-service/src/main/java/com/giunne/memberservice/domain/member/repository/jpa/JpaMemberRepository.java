package com.giunne.memberservice.domain.member.repository.jpa;

import com.giunne.memberservice.domain.member.repository.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByLoginId_LoginId(String loginId);
}
