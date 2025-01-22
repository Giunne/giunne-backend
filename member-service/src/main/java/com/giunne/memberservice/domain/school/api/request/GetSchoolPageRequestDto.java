package com.giunne.memberservice.domain.school.api.request;

import com.giunne.commonservice.domain.common.Pageable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetSchoolPageRequestDto extends Pageable {
    private String name;
}
