package net.class101.homework1.domain.product.application;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.domain.product.dao.ProductRepository;
import net.class101.homework1.domain.product.domain.Product;

@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product findById(Integer id) {
        return (Product) productRepository.findById(id).orElse(null);
    }
}
