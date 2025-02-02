package com.giunne.memberservice.domain.avatar.application;

import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.memberservice.domain.avatar.application.dto.reqeuest.CreateAvatarRequestDto;
import com.giunne.memberservice.domain.avatar.application.dto.response.CreateAvatarResponseDto;
import com.giunne.memberservice.domain.avatar.application.interfaces.AvatarRepository;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.avatar.domain.type.Nickname;
import com.giunne.memberservice.domain.member.application.MemberService;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.recreation.application.RecreationService;
import com.giunne.memberservice.domain.recreation.application.dto.response.CreateRecreationResponseDto;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import com.giunne.memberservice.domain.school.application.SchoolService;
import com.giunne.memberservice.domain.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarService {
    private final MemberService memberService;
    private final RecreationService recreationService;
    private final AvatarRepository avatarRepository;

    public CreateAvatarResponseDto createAvatar(MemberPrincipal memberPrincipal, CreateAvatarRequestDto dto){
        Member member = memberService.getMember(memberPrincipal.getMemberId());
        Recreation recreation = recreationService.getRecreation(dto.recreationId());
        Avatar avatar = Avatar.builder()
                .nickname(Nickname.from(dto.nickName()))
                .characterNo(dto.characterNo())
                .member(member)
                .recreation(recreation)
                .build();

        Avatar createdAvatar = avatarRepository.createAvatar(avatar);

        return CreateAvatarResponseDto.builder()
                .id(createdAvatar.getId())
                .nickname(createdAvatar.getNickname().getNickname())
                .recreationId(createdAvatar.getRecreation().getId())
                .exp(createdAvatar.getExp().getExp())
                .level(createdAvatar.getLevel().getLevel())
                .point(createdAvatar.getPoint().getPoint())
                .characterNo(createdAvatar.getCharacterNo())
                .build();
    }

}
