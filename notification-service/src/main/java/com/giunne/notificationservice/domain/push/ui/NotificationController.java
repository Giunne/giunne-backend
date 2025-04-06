package com.giunne.notificationservice.domain.push.ui;

import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.notificationservice.domain.push.application.NotificationService;
import com.giunne.notificationservice.domain.push.application.dto.request.SendNotificationDto;
import com.giunne.notificationservice.domain.push.application.dto.response.GetNotificationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "알림 관리", description = "알림 조회 및 전송")
@RestController
@RequestMapping("/v1/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 전송", description = """
            ## 기능설명
            * 알림 전송
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                              @RequestBody SendNotificationDto requestDto) {
        notificationService.saveNotification(memberPrincipal, requestDto);
        return ResponseEntity.ok("성공");
    }

    @Operation(summary = "알림 전체 읽음", description = """
            ## 기능설명
            * 알림 전체 읽음
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PutMapping("/read-all")
    public ResponseEntity<String> readAllNotificationByTargetId(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal) {
        notificationService.readAllNotificationByTargetId(memberPrincipal);
        return ResponseEntity.ok("성공");
    }

    @Operation(summary = "알림 읽음", description = """
            ## 기능설명
            * 알림 읽음
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PutMapping("/read/{id}")
    public ResponseEntity<String> readNotificationById(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                                       @PathVariable Long id
    ) {
        notificationService.readNotificationById(memberPrincipal, id);
        return ResponseEntity.ok("성공");
    }

    @Operation(summary = "읽지 않은 알림 갯수", description = """
            ## 기능설명
            * 읽지 않은 알림 갯수
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/count")
    public ResponseEntity<Integer> countNotReadNotification(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal
    ) {
        Integer count = notificationService.countNotReadNotification(memberPrincipal);
        return ResponseEntity.ok(count);
    }

    @Operation(summary = "알림 조회", description = """
            ## 기능설명
            * 알림 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping
    public ResponseEntity<PaginationModel<GetNotificationResponseDto>> getNotifications(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal
            , @ParameterObject Pageable dto

    ) {
        PaginationModel<GetNotificationResponseDto> notifications = notificationService.getNotifications(memberPrincipal, dto);
        return ResponseEntity.ok(notifications);
    }
}
