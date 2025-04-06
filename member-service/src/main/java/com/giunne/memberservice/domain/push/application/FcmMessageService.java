package com.giunne.memberservice.domain.push.application;

import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.memberservice.domain.push.application.dto.request.DeleteFcmTokenRequestDto;
import com.giunne.memberservice.domain.push.application.dto.request.SaveFcmTokenRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetFcmTokenResponseDto;
import com.giunne.memberservice.domain.push.application.interfaces.FcmMessageRepository;
import com.giunne.memberservice.domain.push.domain.FcmToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FcmMessageService {

    private final FcmMessageRepository fcmMessageRepository;

    @Transactional
    public void saveFcmToken(MemberPrincipal memberPrincipal, SaveFcmTokenRequestDto dto){
        if (memberPrincipal.getMemberId() == null) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }

        FcmToken fcmToken = FcmToken.builder()
                .memberId(memberPrincipal.getMemberId())
                .token(dto.token())
                .build();
                ;

        fcmMessageRepository.saveFcmToken(fcmToken);
    }

    public List<GetFcmTokenResponseDto> findByMemberId(Long memberId) {
        List<FcmToken> fcmTokens = fcmMessageRepository.findByMemberId(memberId);
        return  fcmTokens.stream().map(i -> GetFcmTokenResponseDto.builder()
                        .token(i.getToken())
                        .memberId(i.getMemberId())
                        .id(i.getId())
                        .build())
                .toList();
    }

    @Transactional
    public void deleteByMemberId(MemberPrincipal memberPrincipal) {
        if (memberPrincipal.getMemberId() == null) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }
         fcmMessageRepository.deleteByMemberId(memberPrincipal.getMemberId());
    }

    @Transactional
    public void deleteByMemberIdAndToken(MemberPrincipal memberPrincipal, DeleteFcmTokenRequestDto dto) {
        if (memberPrincipal.getMemberId() == null) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }
        fcmMessageRepository.deleteByMemberIdAndToken(memberPrincipal.getMemberId(), dto.token());
    }

}
