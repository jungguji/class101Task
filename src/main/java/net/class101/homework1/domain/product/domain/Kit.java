package net.class101.homework1.domain.product.domain;

import lombok.Getter;

import javax.persistence.Entity;

@Getter
@Entity
public class Kit extends Product {
    private Integer stock;

    public Kit(Integer stock) {
        this.stock = stock;
    }
}
