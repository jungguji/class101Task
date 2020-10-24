package net.class101.homework1.domain.product.domain;

import net.class101.homework1.domain.db.InsertTestData;
import net.class101.homework1.domain.product.dao.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    ProductRepository productRepository;

    @BeforeEach
    void setUp() throws SQLException {
        productRepository = new ProductRepository(Product.class);
        InsertTestData.createTable();
        InsertTestData.insertTestData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        InsertTestData.dropTable();
    }

    @Test
    void update_kit_수량추가() {
        //given
        Kit given = (Kit) productRepository.findById(91008).get();

        //when
        given.update(10);

        //then
        assertEquals(0, given.getStock());
    }

    @Test
    void update_klass_수량추가() {
        //given
        Klass given = (Klass) productRepository.findById(16374).get();

        //when
        given.update(100);

        //then
        assertEquals(99999, given.getStock());
    }
}