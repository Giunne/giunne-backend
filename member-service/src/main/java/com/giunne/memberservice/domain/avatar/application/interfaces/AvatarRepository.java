package com.giunne.memberservice.domain.avatar.application.interfaces;

import com.giunne.memberservice.domain.avatar.application.dto.AvatarWithWearingItemResponseDto;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.member.domain.Member;

import java.util.List;

public interface AvatarRepository {
    Avatar createAvatar(Avatar avatar);
    Avatar findById(Long avatarId);
    List<AvatarWithWearingItemResponseDto> getMyAvatarList(Member member);
}
