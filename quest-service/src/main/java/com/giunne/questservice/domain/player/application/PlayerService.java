package com.giunne.questservice.domain.player.application;

import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.CreateQuestStateRequestDto;
import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.UpdatePlayerRequestDto;
import com.giunne.questservice.domain.player.application.interfaces.PlayerRepository;
import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public void updatePlayer(UpdatePlayerRequestDto dto) {

        Optional<PlayerEntity> optionalPlayerEntity = playerRepository.findByAvatarId(dto.avatarId());
        if(optionalPlayerEntity.isEmpty()) {
            return;
        }

        playerRepository.save(new PlayerEntity(
                Player.builder()
                        .id(optionalPlayerEntity.get().getId())
                        .avatarId(dto.avatarId())
                        .userName(dto.userName())
                        .avatarNickname(dto.avatarNickname())
                        .nickname(dto.nickname())
                        .memberId(dto.memberId())
                        .build()
        ));
    }


}
