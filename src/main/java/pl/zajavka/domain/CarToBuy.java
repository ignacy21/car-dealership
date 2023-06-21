package pl.zajavka.domain;

import lombok.*;

import java.math.BigDecimal;


@With
@Value
@Builder
@EqualsAndHashCode(of = "carToBuyId")
@ToString(of = {"carToBuyId", "vin", "brand", "model", "year", "color", "price"})
public class CarToBuy {

    Integer carToBuyId;
    String vin;
    String brand;
    String model;
    Integer year;
    String color;
    BigDecimal price;
    Invoice invoice;
}

