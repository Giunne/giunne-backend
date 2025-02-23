package com.giunne.commonservice.domain.item;

import com.giunne.commonservice.domain.common.EnumMapperType;
import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ItemGrade implements EnumMapperType {

    S("S등급"),
    A("A등급"),
    B("B등급"),
    C("C등급"),
    ;

    private final String grade;

    ItemGrade(String description) {
        this.grade = description;
    }

    public static ItemGrade from(String grade) {
        validate(grade);
        return ItemGrade.valueOf(grade.toUpperCase());
    }

    public static boolean isItemGrad(String grade) {
        List<ItemGrade> roles = Arrays.stream(ItemGrade.values())
                .filter(r -> r.name().equals(grade))
                .toList();

        return !roles.isEmpty();
    }

    private static void validate(String role) {
        if(!ItemGrade.isItemGrad(role.toUpperCase())) {
            throw new BusinessException(ErrorCode.PROFILE_SIZE_LIMIT);
        }
    }

//    public Integer getWeight(GachaType gachaType) {
//        return switch (this) {
//            case S -> gachaType == GachaType.PREMIUM ?
//                    10 : 0;
//            case A -> gachaType == GachaType.PREMIUM ?
//                    20 : 10;
//            case B -> gachaType == GachaType.PREMIUM ?
//                    30 : 40;
//            case C -> gachaType == GachaType.PREMIUM ?
//                    40 : 50;
//            default -> throw new BusinessException(ErrorCode.PROFILE_SIZE_LIMIT);
//        };
//    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getTitle() {
        return this.grade;
    }
}
