package com.giunne.memberservice.domain.recreation.application.interfaces;

import com.giunne.memberservice.domain.recreation.domain.Recreation;

public interface RecreationRepository {
    Recreation createRecreation(Recreation recreation);

}
