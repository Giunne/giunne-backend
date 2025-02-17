package com.giunne.itemservice.domain.item.domain.type;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ItemGrade {

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

    public Double getWeight(GachaType gachaType) {
        return switch (this) {
            case S -> gachaType == GachaType.PREMIUM ?
                    0.1 : 0.0;
            case A -> gachaType == GachaType.PREMIUM ?
                    0.2 : 0.1;
            case B -> gachaType == GachaType.PREMIUM ?
                    0.3 : 0.4;
            case C -> gachaType == GachaType.PREMIUM ?
                    0.4 : 0.5;
            default -> throw new BusinessException(ErrorCode.PROFILE_SIZE_LIMIT);
        };
    }

}
