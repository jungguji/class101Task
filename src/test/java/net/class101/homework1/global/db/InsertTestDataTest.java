package net.class101.homework1.global.db;

import net.class101.homework1.domain.product.domain.Product;
import net.class101.homework1.domain.product.dao.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InsertTestDataTest {

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository(Product.class);
    }

    @Test
    void createTable() throws SQLException {
        InsertTestData.createTable();

        assertThrows(SQLException.class, () -> {
            InsertTestData.createTable();
        });
    }

    @Test
    public void insertTestData() throws SQLException {
        //given
        //when
        InsertTestData.insertTestData();
        List<Product> whens = (List<Product>) productRepository.findAll();

        //then
        assertEquals(20, whens.size());
    }

    @Test
    void dropTable() {
        assertThrows(SQLException.class, () -> {
            InsertTestData.dropTable();
        });
    }
}