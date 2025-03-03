package com.giunne.itemservice.domain.item.application;

import com.giunne.itemservice.domain.item.domain.type.GachaType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Test
    void initGachaType() {
        System.out.println(Arrays.toString(GachaType.values()));
    }
}