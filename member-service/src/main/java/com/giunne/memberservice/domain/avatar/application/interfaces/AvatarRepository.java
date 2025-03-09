package com.giunne.memberservice.domain.avatar.application.interfaces;

import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.avatar.application.dto.reqeuest.GetMyRecreationAvatarRequestDto;
import com.giunne.memberservice.domain.avatar.application.dto.response.AvatarWithWearingItemResponseDto;
import com.giunne.memberservice.domain.avatar.application.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.member.domain.Member;

public interface AvatarRepository {
    Avatar createAvatar(Avatar avatar);
    Avatar findById(Long avatarId);
    PaginationModel<AvatarWithWearingItemResponseDto> getMyAvatarList(Member member, Pageable dto);
    PaginationModel<GetMyRecreationAvatarResponseDto> getMyRecreationStudentList(GetMyRecreationAvatarRequestDto dto);
}
