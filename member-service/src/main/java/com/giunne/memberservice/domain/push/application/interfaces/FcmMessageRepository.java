package com.giunne.memberservice.domain.push.application.interfaces;

import com.giunne.memberservice.domain.push.domain.FcmToken;

import java.util.List;

public interface FcmMessageRepository {
    FcmToken saveFcmToken(FcmToken fcmToken);

    List<FcmToken> findByMemberId(Long memberId);

    int deleteByMemberIdAndToken(Long memberId, String token);

    int deleteByMemberId(Long memberId);
}
