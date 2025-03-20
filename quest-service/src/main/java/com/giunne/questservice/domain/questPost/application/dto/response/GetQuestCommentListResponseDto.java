package com.giunne.questservice.domain.questPost.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetQuestCommentListResponseDto<T> {
    private int totalCount;
    List<T> tableList;
}
