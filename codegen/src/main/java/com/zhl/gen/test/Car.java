package com.zhl.gen.test;

import lombok.Data;

/**
 * @author hailang.zhang
 * @since 2023-02-17
 */
@Data
public class Car {
    private String carName;

    public Car(String carName) {
        this.carName = carName;
    }
}