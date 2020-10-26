package net.class101.homework1.domain.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.class101.homework1.domain.model.ProductType;
import net.class101.homework1.domain.product.exception.SoldOutException;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer price;
    private Integer stock;

    public Product(String name, Integer price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public synchronized void update(Integer orderCount) {
        if (isKlass()) {
            return;
        }

        if (this.stock - orderCount < 0) {
            throw new SoldOutException();
        }

        this.stock -= orderCount;
    }

    public boolean isKlass() {
        return ProductType.KLASS.name().equals(getType());
    }

    public String getType() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}
