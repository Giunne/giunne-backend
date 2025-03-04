package com.giunne.itemservice.domain.gacha.domain;


import com.giunne.itemservice.domain.gacha.domain.type.GachaPair;
import com.giunne.itemservice.domain.item.domain.type.GachaType;
import com.giunne.itemservice.domain.orders.api.response.GetItemOrderGachaResponseDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WeightedRandom {

    private final List<GachaPair<GetItemOrderGachaResponseDto, Double>> candidates;

    public WeightedRandom(GachaType gachaType, List<GetItemOrderGachaResponseDto> itemList) {

        List<GachaPair<GetItemOrderGachaResponseDto, Integer>> target = itemList.stream().map(i -> {
                    Integer weight = gachaType.getItemGradeMap().get(i.getItemGrade());
                    return new GachaPair<>(i, weight);
                }
        ).toList();

        // 1. 총 가중치 합 계산
        double totalWeight = 0;
        for (GachaPair<GetItemOrderGachaResponseDto, Integer> GachaPair : target) {
            totalWeight += GachaPair.weight;
        }

        // 2. 주어진 가중치를 백분율로 치환 (가중치 / 총 가중치)
        List<GachaPair<GetItemOrderGachaResponseDto, Double>> candidates = new ArrayList<>();
        for (GachaPair<GetItemOrderGachaResponseDto, Integer> GachaPair : target) {
            candidates.add(new GachaPair<>(GachaPair.key, GachaPair.weight / totalWeight));
        }

        // 3. 가중치의 오름차순으로 정렬
        candidates.sort(Comparator.comparingDouble(p -> p.weight));
        this.candidates = candidates;
    }

    public GetItemOrderGachaResponseDto getRandom() {
        // 1. 랜덤 기준점 설정
        final double pivot = Math.random();

        // 2. 가중치의 오름차순으로 원소들을 순회하며 가중치를 누적
        double acc = 0;
        for (GachaPair<GetItemOrderGachaResponseDto, Double> GachaPair : candidates) {
            acc += GachaPair.weight;

            // 3. 누적 가중치 값이 기준점 이상이면 종료
            if (pivot <= acc) {
                return GachaPair.key;
            }
        }

        return null;
    }
}

