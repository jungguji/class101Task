package net.class101.homework1.domain.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@DiscriminatorValue(value = "KLASS")
@Entity
public class Klass extends Product {
    public Klass(String name, Integer price) {
        super(name, price, 99999);
    }
}
