package net.class101.homework1.domain.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.class101.homework1.domain.model.ProductType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    @Column(name ="product_type")
    private ProductType type;

    public Product(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
