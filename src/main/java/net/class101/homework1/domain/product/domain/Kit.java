package net.class101.homework1.domain.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@DiscriminatorValue(value = "KIT")
@Entity
public class Kit extends Product {
    private Integer stock;

    public Kit(String name, Integer price, Integer stock) {
        super(name, price);
        this.stock = stock;
    }
}
