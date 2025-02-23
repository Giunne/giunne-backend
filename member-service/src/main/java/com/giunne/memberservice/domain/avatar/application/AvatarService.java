package com.giunne.memberservice.domain.avatar.application;

import com.giunne.commonservice.infra.external.domain.item.client.ItemInfoClient;
import com.giunne.commonservice.infra.external.domain.item.client.dto.request.GetWearingItemsRequestDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.GetWearingItemResponseDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.ItemInfoResponseDto;
import com.giunne.commonservice.jwt.constant.GrantType;
import com.giunne.commonservice.jwt.service.TokenManager;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.avatar.application.dto.AvatarWithWearingItemResponseDto;
import com.giunne.memberservice.domain.avatar.application.dto.reqeuest.CreateAvatarRequestDto;
import com.giunne.memberservice.domain.avatar.application.dto.reqeuest.LoginPlayerRequestDto;
import com.giunne.memberservice.domain.avatar.application.dto.response.CreateAvatarResponseDto;
import com.giunne.memberservice.domain.avatar.application.dto.response.LoginPlayerResponseDto;
import com.giunne.memberservice.domain.avatar.application.interfaces.AvatarRepository;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.avatar.domain.type.*;
import com.giunne.memberservice.domain.inventory.application.InventoryService;
import com.giunne.memberservice.domain.member.application.MemberService;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.recreation.application.RecreationService;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvatarService {
    private final MemberService memberService;
    private final RecreationService recreationService;
    private final AvatarRepository avatarRepository;
    private final TokenManager tokenManager;
    private final ItemInfoClient itemInfoClient;
    private final InventoryService inventoryService;


    @Transactional
    public CreateAvatarResponseDto creatPlayer(MemberPrincipal memberPrincipal, CreateAvatarRequestDto dto){

        Response<ItemInfoResponseDto> itemInfoResponseDtoResponse = itemInfoClient.requestItemInfo(dto.characterNo());
        ItemInfoResponseDto  itemInfoResponseDto = itemInfoResponseDtoResponse.value();
        Member member = memberService.getMember(memberPrincipal.getMemberId());
        Recreation recreation = recreationService.getRecreation(dto.recreationId());
        Avatar avatar = Avatar.builder()
                .nickname(Nickname.from(dto.nickName()))
                .characterNo(dto.characterNo())
                .classRoom(ClassRoom.builder()
                        .grade(dto.grade())
                        .classNumber(dto.classNumber())
                        .studentNumber(dto.studentNumber())
                        .build())
                .exp(Exp.from(0L))
                .level(Level.from(0L))
                .point(Point.from(0L))
                .member(member)
                .characterNo(itemInfoResponseDto.getId())
                .recreation(recreation)
                .build();


        Avatar createdAvatar = avatarRepository.createAvatar(avatar);

        inventoryService.insertInventory(createdAvatar, itemInfoResponseDto);
        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(memberPrincipal.getMemberId(), createdAvatar.getId(), memberPrincipal.getRole(), accessTokenExpireTime);

        return CreateAvatarResponseDto.builder()
                .id(createdAvatar.getId())
                .nickname(createdAvatar.getNickname().getNickname())
                .recreationId(createdAvatar.getRecreation().getId())
                .exp(createdAvatar.getExp().getExp())
                .level(createdAvatar.getLevel().getLevel())
                .point(createdAvatar.getPoint().getPoint())
                .characterNo(createdAvatar.getCharacterNo())
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }

    public LoginPlayerResponseDto loginPlayer(MemberPrincipal memberPrincipal, LoginPlayerRequestDto dto) {
        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(memberPrincipal.getMemberId(), dto.playerId(), memberPrincipal.getRole(), accessTokenExpireTime);
        Avatar avatar = avatarRepository.findById(dto.playerId());

        return LoginPlayerResponseDto.builder()
                .id(avatar.getId())
                .nickname(avatar.getNickname().getNickname())
                .recreationId(avatar.getRecreation().getId())
                .exp(avatar.getExp().getExp())
                .level(avatar.getLevel().getLevel())
                .point(avatar.getPoint().getPoint())
                .characterNo(avatar.getCharacterNo())
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }


    public List<AvatarWithWearingItemResponseDto> getMyAvatarList(MemberPrincipal memberPrincipal) {
        Member member = memberService.getMember(memberPrincipal.getMemberId());

        List<AvatarWithWearingItemResponseDto> myAvatarList = avatarRepository.getMyAvatarList(member);

        for (int i = 0; i < myAvatarList.size(); i++) {
            GetWearingItemsRequestDto getWearingItemsRequestDto = new GetWearingItemsRequestDto(myAvatarList.get(i).getWearingItemIds());
            Response<List<GetWearingItemResponseDto>> listResponse = itemInfoClient.requestFindWearingItems(getWearingItemsRequestDto);
            myAvatarList.get(i).setWearingItems(listResponse.value());
        }

        return myAvatarList;
    }

}
