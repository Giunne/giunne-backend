package com.giunne.itemservice.domain.orders.domain.converter;

import com.giunne.commonservice.type.converter.AbstracEnumAttributeConverter;
import com.giunne.itemservice.domain.orders.domain.constant.OrderType;
import jakarta.persistence.Converter;

@Converter
public class OrderTypeConverter extends AbstracEnumAttributeConverter<OrderType> {

    public static final String ENUM_NAME = "주문타입";

    public OrderTypeConverter() {
        super(OrderType.class, ENUM_NAME);
    }
}
