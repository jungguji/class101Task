package net.class101.homework1.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public
class ViewProduct {
    private Integer id;
    private String name;
    private Integer price;
    private Integer stock;

    @Builder
    public ViewProduct(Integer id, String name, Integer price, Integer stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
