package com.giunne.itemservice.domain.orders.respository.entity;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.itemservice.domain.item.repository.entity.ItemEntity;
import com.giunne.itemservice.domain.orders.domain.constant.OrderType;
import com.giunne.itemservice.domain.orders.domain.converter.OrderTypeConverter;
import com.giunne.itemservice.domain.orders.domain.type.Quantity;
import com.giunne.itemservice.domain.orders.domain.type.TotalPrice;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_no")
    private Long id; // 주문 번호

    @Embedded
    private Quantity quantity; // 주문 수량

    @Embedded
    private TotalPrice totalPrice; // 총 주문 금액

    @Convert(converter = OrderTypeConverter.class)
    private OrderType orderType; // 주문타입

    @Column(name = "player_no")
    private Long characterNo; // 캐릭터 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_no")
    private ItemEntity itemEntity; // 아이템
}
