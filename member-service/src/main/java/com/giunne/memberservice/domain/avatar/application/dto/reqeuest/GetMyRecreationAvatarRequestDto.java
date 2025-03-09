package com.giunne.memberservice.domain.avatar.application.dto.reqeuest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "레크레이션내 학생정보 요청DTO")
public class GetMyRecreationAvatarRequestDto {

    @Parameter(
            description = "레크레이션 번호",
            example = "1"
    )
    @NotNull
    private Long recreationId;
}
