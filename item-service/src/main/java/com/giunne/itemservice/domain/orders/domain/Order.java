package com.giunne.itemservice.domain.orders.domain;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.orders.domain.constant.OrderType;
import com.giunne.itemservice.domain.orders.domain.converter.OrderTypeConverter;
import com.giunne.itemservice.domain.orders.domain.type.Quantity;
import com.giunne.itemservice.domain.orders.domain.type.TotalPrice;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

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
    private Item item; // 아이템
}
