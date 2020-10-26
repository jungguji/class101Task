package net.class101.homework1.domain.order.controller;

import net.class101.homework1.domain.order.domain.OrderHistory;
import net.class101.homework1.domain.product.dao.ProductRepository;
import net.class101.homework1.domain.product.domain.Kit;
import net.class101.homework1.domain.product.domain.Klass;
import net.class101.homework1.domain.product.domain.Product;
import net.class101.homework1.global.common.Response;
import net.class101.homework1.global.common.StatusCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderControllerTest {

    private static final String name = "test";
    private static final int price = 10000;
    private OrderController orderController;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        orderController = new OrderController();
        productRepository = new ProductRepository(Product.class);
    }

    @Test
    void orderOrQuit() {
        Response exit = orderController.orderOrQuit("q");
        Response order = orderController.orderOrQuit("o");
        Response badRequest = orderController.orderOrQuit("r");

        assertEquals(StatusCode.EXIT.getCode(), exit.getCode());
        assertEquals(StatusCode.OK.getCode(), order.getCode());
        assertEquals(StatusCode.BAD_REQUEST.getCode(), badRequest.getCode());
    }

    @Test
    void addOrder_Bad_request() {
        //given
        Product product = new Klass(name, price);
        int count = 2;

        productRepository.save(product);

        OrderHistory orderHistory = new OrderHistory();

        //when
        Response response = this.orderController.addOrder(orderHistory, product, count);

        //then

        assertEquals(StatusCode.BAD_REQUEST.getCode(), response.getCode());
    }

    @Test
    void addOrder_OK() {
        //given
        Product product = new Kit(name, price, 100);
        int count = 5;

        productRepository.save(product);

        OrderHistory orderHistory = new OrderHistory();

        //when
        Response response = this.orderController.addOrder(orderHistory, product, count);

        //then
        assertEquals(StatusCode.OK.getCode(), response.getCode());
    }
}