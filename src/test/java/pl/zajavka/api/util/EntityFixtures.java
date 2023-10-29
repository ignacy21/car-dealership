package pl.zajavka.api.util;

import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;

import java.math.BigDecimal;

public class EntityFixtures {

    public static CarToBuyEntity someCar1() {
        return CarToBuyEntity.builder()
                .vin("vin1")
                .brand("brand1")
                .model("model1")
                .year(2001)
                .color("color1")
                .price(new BigDecimal("10000"))
                .build();
    }
    public static CarToBuyEntity someCar2() {
        return CarToBuyEntity.builder()
                .vin("vin2")
                .brand("brand2")
                .model("model2")
                .year(2002)
                .color("color2")
                .price(new BigDecimal("20000"))
                .build();
    }
    public static CarToBuyEntity someCar3() {
        return CarToBuyEntity.builder()
                .vin("vin3")
                .brand("brand3")
                .model("model3")
                .year(3003)
                .color("color3")
                .price(new BigDecimal("30000"))
                .build();
    }
}
