package com.giunne.notificationservice.domain.push.ui;

import com.giunne.commonservice.ui.Response;
import com.giunne.notificationservice.domain.push.application.dto.request.SendFcmMessageDto;
import com.giunne.notificationservice.domain.push.application.FirebaseMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "FCM 관리", description = "FCM 전송")
@RestController
@RequestMapping("/v1/api/fcm")
@RequiredArgsConstructor
public class FirebaseController {

    private final FirebaseMessageService firebaseMessageService;

    @Operation(summary = "FCM 전송", description = """
            ## 기능설명
            * FCM 전송
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/send-message")
    public Response<String> sendMessage(@RequestBody SendFcmMessageDto requestDto) {
        firebaseMessageService.sendMessage(requestDto);
        return Response.ok("성공");
    }
}
