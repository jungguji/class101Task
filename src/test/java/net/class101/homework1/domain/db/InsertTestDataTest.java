package net.class101.homework1.domain.db;

import net.class101.homework1.domain.product.domain.Product;
import net.class101.homework1.domain.product.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InsertTestDataTest {

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository(Product.class);
    }

    @Test
    public void insertTestData() {
        //given
        //when
        InsertTestData.insertTestData();
        List<Product> whens = (List<Product>) productRepository.findAll();

        //then
        assertEquals(20, whens.size());
    }
}