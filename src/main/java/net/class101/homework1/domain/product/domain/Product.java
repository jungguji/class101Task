package net.class101.homework1.domain.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.class101.homework1.domain.model.ProductType;
import net.class101.homework1.domain.product.exception.SoldOutException;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(name ="product_type")
    private ProductType type;

    private Integer stock;

    public Product(String name, Integer price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;

    }

    public void update(Integer orderCount) {
        if (isKlass()) {
            return;
        }

        if (this.stock - orderCount < 0) {
            throw new SoldOutException();
        }

        this.stock -= orderCount;
    }

    public boolean isKlass() {
        return ProductType.KLASS.equals(this.type);
    }
}
