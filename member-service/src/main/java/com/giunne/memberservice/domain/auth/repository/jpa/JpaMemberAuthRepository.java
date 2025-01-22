package com.giunne.memberservice.domain.auth.repository.jpa;


import com.giunne.memberservice.domain.auth.repository.entity.MemberAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaMemberAuthRepository extends JpaRepository<MemberAuthEntity, Long> {

    Optional<MemberAuthEntity> findByLoginId(String loginId);
}
