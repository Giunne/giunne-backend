package com.giunne.itemservice.domain.orders.domain;

import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.orders.domain.constant.OrderType;
import com.giunne.itemservice.domain.orders.domain.type.Quantity;
import com.giunne.itemservice.domain.orders.domain.type.TotalPrice;

public class Order {
    private Long id; // 주문 번호
    private Quantity quantity; // 주문 수량
    private TotalPrice totalPrice; // 총 주문 금액
    private OrderType orderType; // 주문타입
    private Long characterNo; // 캐릭터 번호
    private Item item; // 아이템
}
