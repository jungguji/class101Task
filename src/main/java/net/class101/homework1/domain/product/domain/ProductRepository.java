package net.class101.homework1.domain.product.domain;

import net.class101.homework1.domain.db.DAOCommon;

public class ProductRepository<Product, Long> extends DAOCommon<Product, Long> {
    public ProductRepository(java.lang.Class<Product> type) {
        super(type);
    }
}
