package net.class101.homework1.domain.product.controller;

import net.class101.homework1.domain.db.InsertTestData;
import net.class101.homework1.domain.product.application.ProductService;
import net.class101.homework1.domain.product.dao.ProductRepository;
import net.class101.homework1.domain.product.domain.Product;
import net.class101.homework1.global.cmmon.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductControllerTest {

    ProductController productController;
    ProductService productService;

    @BeforeEach
    void setUp() throws SQLException {
        ProductRepository productRepository = new ProductRepository(Product.class);
        productService = new ProductService(productRepository);
        productController = new ProductController(productService);

        InsertTestData.createTable();
        InsertTestData.insertTestData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        InsertTestData.dropTable();
    }

    @Test
    void update() {
        //given
        int id = 16374;
        int count = 100;

        Product given = this.productService.findById(id);

        //when
        Response response = this.productController.update(id, count);

        //then
        assertEquals(response.getCode(), 200);
        assertEquals(response.getMessage(), "OK");
        Product when = (Product) response.getBody();

        assertEquals(given.getName(), when.getName());
        assertEquals(given.getPrice(), when.getPrice());
        assertEquals(given.getStock(), when.getStock());
    }

    @Test
    void update_null() {
        //given
        int id = 1;
        int count = 100;

        int code = 404;
        StringBuilder sb = new StringBuilder();
        sb.append("해당하는 상품번호의 상품이 존재하지 않습니다.\n");
        sb.append("상품번호를 다시 한번 확인하여 주세요.");

        //when
        Response response = this.productController.update(id, count);

        //then
        assertEquals(code , response.getCode());
        assertEquals(sb.toString(), response.getMessage());
        assertNull(response.getBody());
    }
}