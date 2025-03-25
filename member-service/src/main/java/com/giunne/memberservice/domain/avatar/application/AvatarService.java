package com.giunne.memberservice.domain.avatar.application;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.infra.external.domain.item.client.ItemInfoClient;
import com.giunne.commonservice.infra.external.domain.item.client.dto.request.GetWearingItemsRequestDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.GetWearingItemResponseDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.ItemInfoResponseDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.GetAvatarProfileListRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.GetAvatarProfileRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.UpdateExpRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.UpdatePointRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyPointResponseDto;
import com.giunne.commonservice.infra.external.domain.quest.client.QuestInfoClient;
import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.CreateQuestStateRequestDto;
import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.UpdatePlayerRequestDto;
import com.giunne.commonservice.jwt.constant.GrantType;
import com.giunne.commonservice.jwt.service.TokenManager;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.avatar.application.dto.reqeuest.*;
import com.giunne.memberservice.domain.avatar.application.dto.response.AvatarWithWearingItemResponseDto;
import com.giunne.memberservice.domain.avatar.application.dto.response.CreateAvatarResponseDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.memberservice.domain.avatar.application.dto.response.LoginPlayerResponseDto;
import com.giunne.memberservice.domain.avatar.application.interfaces.AvatarRepository;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.avatar.domain.type.*;
import com.giunne.memberservice.domain.inventory.application.InventoryService;
import com.giunne.memberservice.domain.levelUpPolicy.application.interfaces.LevelUpPolicyRepository;
import com.giunne.memberservice.domain.levelUpPolicy.domain.LevelUpPolicy;
import com.giunne.memberservice.domain.member.application.MemberService;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.recreation.application.RecreationService;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AvatarService {
    private final MemberService memberService;
    private final RecreationService recreationService;
    private final AvatarRepository avatarRepository;
    private final TokenManager tokenManager;
    private final ItemInfoClient itemInfoClient;
    private final InventoryService inventoryService;
    private final LevelUpPolicyRepository levelUpPolicyRepository;
    private final QuestInfoClient questInfoClient;

    @Transactional
    public CreateAvatarResponseDto creatPlayer(MemberPrincipal memberPrincipal, CreateAvatarRequestDto dto) {

        Response<ItemInfoResponseDto> itemInfoResponseDtoResponse = itemInfoClient.requestItemInfo(dto.characterNo());
        ItemInfoResponseDto itemInfoResponseDto = itemInfoResponseDtoResponse.value();
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
                .level(Level.from(1L))
                .point(Point.from(0L))
                .member(member)
                .characterNo(itemInfoResponseDto.getId())
                .recreation(recreation)
                .build();


        Avatar createdAvatar = avatarRepository.createAvatar(avatar);

        inventoryService.insertInventory(createdAvatar, itemInfoResponseDto);
        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(memberPrincipal.getMemberId(), createdAvatar.getId(), memberPrincipal.getRole(), accessTokenExpireTime);


        // TODO: 로드맵 번호 하드코딩 수정예정
        questInfoClient.savePlayerQuestStates(
                CreateQuestStateRequestDto.builder()
                        .roadMapId(1L)
                        .memberId(member.getId())
                        .userName(member.getUserName().getUserName())
                        .nickname(member.getNickname().getNickname())
                        .avatarId(createdAvatar.getId())
                        .avatarNickname(createdAvatar.getNickname().getNickname())
                        .build()
        );
        // TODO: 로드맵 번호 하드코딩 수정예정
        questInfoClient.savePlayerQuestStates(
                CreateQuestStateRequestDto.builder()
                        .roadMapId(2L)
                        .memberId(member.getId())
                        .userName(member.getUserName().getUserName())
                        .nickname(member.getNickname().getNickname())
                        .avatarId(createdAvatar.getId())
                        .avatarNickname(createdAvatar.getNickname().getNickname())
                        .build()
        );

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

        LevelUpPolicy level = levelUpPolicyRepository.findByCurrentLevel(avatar.getLevel().getLevel());
        Member member = memberService.getMember(memberPrincipal.getMemberId());

        questInfoClient.updatePlayer(
                UpdatePlayerRequestDto.builder()
                        .userName(member.getNickname().getNickname())
                        .memberId(member.getId())
                        .userName(member.getUserName().getUserName())
                        .nickname(member.getNickname().getNickname())
                        .avatarId(avatar.getId())
                        .avatarNickname(avatar.getNickname().getNickname())
                        .build()
        );


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
                .needExp(level != null ? level.getNeedExp().getValue() : 0)
                .build();
    }


    public PaginationModel<AvatarWithWearingItemResponseDto> getMyAvatarList(MemberPrincipal memberPrincipal, Pageable dto) {
        Member member = memberService.getMember(memberPrincipal.getMemberId());

        PaginationModel<AvatarWithWearingItemResponseDto> paginationModel = avatarRepository.getMyAvatarList(member, dto);
        List<AvatarWithWearingItemResponseDto> myAvatarList = paginationModel.getData();
        for (int i = 0; i < myAvatarList.size(); i++) {
            GetWearingItemsRequestDto getWearingItemsRequestDto = new GetWearingItemsRequestDto(myAvatarList.get(i).getWearingItemIds(), myAvatarList.get(i).getLevel());
            Response<List<GetWearingItemResponseDto>> listResponse = itemInfoClient.requestFindWearingItems(getWearingItemsRequestDto);
            myAvatarList.get(i).setWearingItems(listResponse.value());
        }

        return paginationModel;
    }


    public List<GetMyRecreationAvatarResponseDto> getMyRecreationStudentList(MemberPrincipal memberPrincipal, GetMyRecreationAvatarRequestDto dto) {

        List<GetMyRecreationAvatarResponseDto> myAvatarList = avatarRepository.getMyRecreationStudentList(memberPrincipal.getPlayerId(), dto);
        for (GetMyRecreationAvatarResponseDto getMyRecreationAvatarResponseDto : myAvatarList) {
            GetWearingItemsRequestDto getWearingItemsRequestDto = new GetWearingItemsRequestDto(getMyRecreationAvatarResponseDto.getWearingItemIds(), getMyRecreationAvatarResponseDto.getLevel());
            Response<List<GetWearingItemResponseDto>> listResponse = itemInfoClient.requestFindWearingItems(getWearingItemsRequestDto);
            getMyRecreationAvatarResponseDto.setWearingItems(listResponse.value());
        }

        return myAvatarList;
    }


    public GetMyRecreationAvatarResponseDto getAvatarProfileInfo(GetAvatarProfileRequestDto dto) {
        GetMyRecreationAvatarResponseDto getMyRecreationAvatarResponseDto = avatarRepository.getAvatarProfileInfo(dto.getPlayerId());
        GetWearingItemsRequestDto getWearingItemsRequestDto = new GetWearingItemsRequestDto(getMyRecreationAvatarResponseDto.getWearingItemIds(), getMyRecreationAvatarResponseDto.getLevel());
        Response<List<GetWearingItemResponseDto>> listResponse = itemInfoClient.requestFindWearingItems(getWearingItemsRequestDto);
        getMyRecreationAvatarResponseDto.setWearingItems(listResponse.value());
        return getMyRecreationAvatarResponseDto;
    }

    public List<GetMyRecreationAvatarResponseDto> getAvatarProfileListInfo(GetAvatarProfileListRequestDto dto) {
        List<GetMyRecreationAvatarResponseDto> myAvatarList = avatarRepository.getAvatarProfileListInfo(dto);
        for (GetMyRecreationAvatarResponseDto getMyRecreationAvatarResponseDto : myAvatarList) {
            GetWearingItemsRequestDto getWearingItemsRequestDto = new GetWearingItemsRequestDto(getMyRecreationAvatarResponseDto.getWearingItemIds(), getMyRecreationAvatarResponseDto.getLevel());
            Response<List<GetWearingItemResponseDto>> listResponse = itemInfoClient.requestFindWearingItems(getWearingItemsRequestDto);
            getMyRecreationAvatarResponseDto.setWearingItems(listResponse.value());
        }
        return myAvatarList;
    }

    @Transactional
    public void increaseExperience(UpdateExpRequestDto dto) {
        Avatar avatar = avatarRepository.findById(dto.getPlayerId());
        avatar.getExp().increaseExp(dto.getExp());
        levelUp(avatar);
        avatarRepository.save(avatar);
    }

    private void levelUp(Avatar avatar) {

        while (true) {
            boolean isNotLevelUp = true;
            LevelUpPolicy level = levelUpPolicyRepository.findByCurrentLevel(avatar.getLevel().getLevel());

            // 만렙인경우
            if(level.isMaxLevel()) {
                return;
            }

            if(level.getNeedExp().getValue() <= avatar.getExp().getExp()) {
                avatar.getExp().updateExp(avatar.getExp().getExp() - level.getNeedExp().getValue());
                avatar.getLevel().increaseLevel();
                isNotLevelUp = false;
            }

            if(isNotLevelUp) {
                return;
            }
        }
    }

    @Transactional
    public void increasePoint(UpdatePointRequestDto dto) {
        Avatar avatar = avatarRepository.findById(dto.getPlayerId());
        avatar.getPoint().increasePoint(dto.getPoint());
        avatarRepository.save(avatar);
    }

    @Transactional
    public void decreasePoint(UpdatePointRequestDto dto) {
        Avatar avatar = avatarRepository.findById(dto.getPlayerId());
        avatar.getPoint().decreasePoint(dto.getPoint());
        avatarRepository.save(avatar);
    }


    public GetMyPointResponseDto getMyPoint(MemberPrincipal memberPrincipal) {
        Avatar avatar = avatarRepository.findById(memberPrincipal.getPlayerId());
        return GetMyPointResponseDto
                .builder()
                .point(avatar.getPoint().getPoint())
                .build();
    }
}
