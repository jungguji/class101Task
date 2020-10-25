package net.class101.homework1.domain.product.application;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.domain.product.dao.ProductRepository;
import net.class101.homework1.domain.product.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    public Product findById(Integer id) {
        return (Product) productRepository.findById(id).orElse(null);
    }

    public Product update(Integer id, Integer count) {
        Optional<Product> optional = this.productRepository.findById(id);

        if (!optional.isPresent()) {
            return null;
        }

        Product product = optional.get();
        synchronized(this) {
            product.update(count);
        }

        this.productRepository.save(product);

        return product;
    }
}
