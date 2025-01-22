package com.giunne.memberservice.domain.member.repository;

import com.giunne.commonservice.error.exception.BusinessException;
import com.giunne.memberservice.domain.member.application.interfaces.MemberRepository;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.member.domain.exception.ErrorCode;
import com.giunne.memberservice.domain.member.repository.entity.MemberEntity;
import com.giunne.memberservice.domain.member.repository.jpa.JpaMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@AllArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public Member save(Member member) {
        MemberEntity entity = new MemberEntity(member);
        entity = jpaMemberRepository.save(entity);
        return entity.toMember();
    }

    @Override
    public Member findById(Long id) {
        MemberEntity userEntity = jpaMemberRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return userEntity.toMember();
    }

    @Override
    public void validateDuplicateMember(String loginId) {
        Optional<MemberEntity> optionalMemberEntity = jpaMemberRepository.findByLoginId_LoginId(loginId);
        if(optionalMemberEntity.isPresent()){
            throw new BusinessException(ErrorCode.DUPLICATE_MEMBER_EXIST);
        }
    }
}
