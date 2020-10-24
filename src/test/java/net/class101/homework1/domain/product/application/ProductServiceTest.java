package net.class101.homework1.domain.product.application;

import net.class101.homework1.domain.db.InsertTestData;
import net.class101.homework1.domain.product.dao.ProductRepository;
import net.class101.homework1.domain.product.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() throws SQLException {
        ProductRepository productRepository = new ProductRepository(Product.class);
        productService = new ProductService(productRepository);

        InsertTestData.createTable();
        InsertTestData.insertTestData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        InsertTestData.dropTable();
    }

    @Test
    void findById() {
        //given
        //when
        Product product = productService.findById(16374);

        //then
        assertEquals(16374, product.getId());
        assertEquals("KLASS", product.getType());
        assertEquals("스마트스토어로 월 100만원 만들기, 평범한 사람이 돈을 만드는 비법", product.getName());
        assertEquals(151950, product.getPrice());
        assertEquals(99999, product.getStock());
    }

    @Test
    void findById_null() {
        //given
        //when
        Product product = productService.findById(1);
        //then
        assertNull(product);
    }
}