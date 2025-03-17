package com.giunne.questservice.domain.questState.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Schema(description = "퀘스트(로드맵) 인증DTO")
public record CertificateQuestRequestDto(
        @Schema(
                description = "퀘스트 번호",
                example = "1"
        )
        Long questId
) {
}
