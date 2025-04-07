package com.giunne.commonservice.infra.external.domain.notification.client;

import com.giunne.commonservice.infra.external.domain.notification.client.dto.request.SendNotificationDto;
import com.giunne.commonservice.ui.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.CompletableFuture;

@FeignClient(url = "${app.apiUrl.notification-service}", name = "notificationInfoClient")
public interface NotificationInfoClient {

    @PostMapping("/send-message")
    Response<String> sendMessage(@RequestBody SendNotificationDto requestDto);

    // 비동기 메소드 추가
    @PostMapping("/send-message")
    CompletableFuture<Response<String>> sendMessageAsync(@RequestBody SendNotificationDto requestDto);

}
