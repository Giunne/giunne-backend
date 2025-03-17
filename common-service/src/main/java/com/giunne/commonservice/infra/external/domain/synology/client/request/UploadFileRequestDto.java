package com.giunne.commonservice.infra.external.domain.synology.client.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Schema(description = "파일 업로드 요청DTO")
public record UploadFileRequestDto(
        MultipartFile file
) {
}