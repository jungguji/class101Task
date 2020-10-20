package net.class101.homework1.domain.product.domain;

import net.class101.homework1.domain.model.ProductType;

import javax.persistence.*;

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
}
