package com.giunne.memberservice.domain.avatar.application.interfaces;

import com.giunne.memberservice.domain.avatar.domain.Avatar;

public interface AvatarRepository {
    Avatar createAvatar(Avatar avatar);
}
