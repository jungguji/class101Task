package net.class101.homework1.domain.order.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Order {
    private Integer id;
    private String name;
    private Integer quantity;
    private Integer price;
    private String type;

    @Builder
    public Order(Integer id, String name, Integer quantity, Integer price, String type) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = calculate(price);
        this.type = type;
    }

    private int calculate(Integer price) {
        return this.quantity * price;
    }

    public void add(Integer amount, Integer price) {
        this.quantity += amount;
        this.price = calculate(price);
    }
}
