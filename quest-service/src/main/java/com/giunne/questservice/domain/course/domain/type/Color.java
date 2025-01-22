package com.giunne.questservice.domain.course.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 색상
 */

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Color {

    @Column(name = "red")
    private  Integer red;

    @Column(name = "green")
    private  Integer green;

    @Column(name = "blue")
    private  Integer blue;


    private Color(final Integer red,final Integer green,final Integer blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static Color from(final Integer red,final Integer green,final Integer blue) {
        return new Color(red, green, blue);
    }

}
