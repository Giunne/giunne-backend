package com.giunne.notificationservice.domain.push.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/*
 * @author : giunne
 * @date : 2023/10/12
 * @description : 모바일에서 전달받은 객체를 매핑하는 DTO
 */

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmSendDto {

    @Schema(description = "디바이스 기기에서 발급받은 FCM 토큰 값")
    private String token;

    @Schema(description = "푸시메시지의 제목")
    private String title;

    @Schema(description = "푸시메시지의 내용")
    private String body;


    @Builder(toBuilder = true)
    public FcmSendDto(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }
}
