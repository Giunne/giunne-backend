package com.giunne.notificationservice.domain.push.application;

import com.giunne.commonservice.infra.external.domain.member.client.MemberInfoClient;
import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetFcmTokenResponseDto;
import com.giunne.commonservice.ui.Response;
import com.giunne.notificationservice.domain.push.application.dto.request.SendFcmMessageDto;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseMessageService {

    private final MemberInfoClient memberInfoClient;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendMessage(SendFcmMessageDto requestDto) {
        // 사용자의 Firebase 토큰 값을 조회
        Response<List<GetFcmTokenResponseDto>> fcmTokenResponse = memberInfoClient.findFcmTokenByMemberId(requestDto.getMemberId());

        if(fcmTokenResponse.code() != HttpStatus.OK.value()) {
            throw new IllegalArgumentException("알림 전송을 할 수 없습니다.");
        }

        List<GetFcmTokenResponseDto> fcmTokens = fcmTokenResponse.value();

        for(GetFcmTokenResponseDto token : fcmTokens) {
            if(token.getToken() == null || token.getToken().isEmpty()) {
                continue;
            }

            String userFirebaseToken = token.getToken();
            Notification notification = Notification.builder()
                    .setTitle(requestDto.getTitle())
                    .setBody(requestDto.getContent())
                    .build();

            // 메시지 구성
            Message message = Message.builder()
                    .setToken(userFirebaseToken) // 조회한 토큰 값을 사용
                    .setNotification(notification)
                    .build();
            try {
                 FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                if (e.getMessagingErrorCode().equals(MessagingErrorCode.INVALID_ARGUMENT)) {
                    // 토큰이 유효하지 않은 경우, 오류 코드를 반환
                    log.error(e.getMessagingErrorCode().toString());
                } else if (e.getMessagingErrorCode().equals(MessagingErrorCode.UNREGISTERED)) {
                    // 재발급된 이전 토큰인 경우, 오류 코드를 반환
                    log.error(e.getMessagingErrorCode().toString());
                } else { // 그 외, 오류는 런타임 예외로 처리
                    log.error(e.getMessagingErrorCode().toString());
                }
            }
        };

    }
}
