package com.giunne.itemservice.domain.item.domain.converter;

import com.giunne.commonservice.type.converter.AbstracEnumAttributeConverter;
import com.giunne.itemservice.domain.item.domain.constant.ItemType;
import jakarta.persistence.Converter;

@Converter
public class ItemTypeConverter extends AbstracEnumAttributeConverter<ItemType> {

    public static final String ENUM_NAME = "캐릭터타입";

    public ItemTypeConverter() {
        super(ItemType.class, ENUM_NAME);
    }
}
