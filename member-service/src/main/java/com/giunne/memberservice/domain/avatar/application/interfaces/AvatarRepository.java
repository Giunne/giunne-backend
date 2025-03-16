package com.giunne.memberservice.domain.avatar.application.interfaces;

import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.avatar.application.dto.reqeuest.GetAvatarProfileListRequestDto;
import com.giunne.memberservice.domain.avatar.application.dto.reqeuest.GetMyRecreationAvatarRequestDto;
import com.giunne.memberservice.domain.avatar.application.dto.response.AvatarWithWearingItemResponseDto;
import com.giunne.memberservice.domain.avatar.application.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.member.domain.Member;

import java.util.List;

public interface AvatarRepository {
    Avatar createAvatar(Avatar avatar);
    Avatar findById(Long avatarId);
    PaginationModel<AvatarWithWearingItemResponseDto> getMyAvatarList(Member member, Pageable dto);
    List<GetMyRecreationAvatarResponseDto> getMyRecreationStudentList(Long playerId, GetMyRecreationAvatarRequestDto dto);
    GetMyRecreationAvatarResponseDto getAvatarProfileInfo(Long playerId);
    List<GetMyRecreationAvatarResponseDto> getAvatarProfileListInfo(GetAvatarProfileListRequestDto dto);
}
