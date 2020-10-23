package net.class101.homework1.domain.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Order {
    private Integer id;
    private String name;
    private Integer amount;
    private Integer price;
    private String type;

    @Builder
    public Order(Integer id, String name, Integer amount, Integer price, String type) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = calculate(price);
        this.type = type;
    }

    private int calculate(Integer price) {
        return this.amount * price;
    }

    public void add(Integer amount, Integer price) {
        this.amount += amount;
        this.price = calculate(price);
    }
}
