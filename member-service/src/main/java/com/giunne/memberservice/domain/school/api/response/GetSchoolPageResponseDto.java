package com.giunne.memberservice.domain.school.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetSchoolPageResponseDto {
    private Long id;
    private String name;
}
