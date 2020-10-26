package net.class101.homework1.domain.product.dao;

import net.class101.homework1.global.db.DAOCommon;

public class ProductRepository<Product, Long> extends DAOCommon<Product, Long> {
    public ProductRepository(java.lang.Class<Product> type) {
        super(type);
    }
}
