package com.giunne.itemservice.domain.item.application;

import com.giunne.itemservice.domain.gacha.domain.WeightedRandom;
import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.item.domain.type.GachaType;

import com.giunne.itemservice.domain.orders.api.response.GetItemOrderGachaResponseDto;
import com.giunne.itemservice.domain.orders.application.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;


    @Test
    void initGachaType() {
        System.out.println(Arrays.toString(GachaType.values()));
        System.out.println(GachaType.GENERAL.name());
        System.out.println(GachaType.from("GENERAL"));
    }

//    @Test
//    void orderGacha() {
//        GetItemOrderGachaResponseDto item = orderService.orderGacha("GENERAL");
//        System.out.println(item.getId() + ": " + item.getItemName());
//    }
}