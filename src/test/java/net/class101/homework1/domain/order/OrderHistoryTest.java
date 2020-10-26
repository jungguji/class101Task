package net.class101.homework1.domain.order;

import net.class101.homework1.domain.db.InsertTestData;
import net.class101.homework1.domain.model.ProductType;
import net.class101.homework1.domain.order.domain.Order;
import net.class101.homework1.domain.order.domain.OrderHistory;
import net.class101.homework1.domain.product.dao.ProductRepository;
import net.class101.homework1.domain.product.domain.Kit;
import net.class101.homework1.domain.product.domain.Klass;
import net.class101.homework1.domain.product.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OrderHistoryTest {

    private static final int id = 1;
    private static final String name = "test";
    private static final int amount = 100;
    private static final int price = 10000;

    OrderHistory orderHistory;
    ProductRepository productRepository;

    @BeforeEach
    void setUp() throws SQLException {
        orderHistory = new OrderHistory();
        productRepository = new ProductRepository(Product.class);

        InsertTestData.createTable();
        InsertTestData.insertTestData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        InsertTestData.dropTable();
    }

    private Order createOrder(String type) {
        return Order.builder()
                .id(id)
                .name(name)
                .quantity(amount)
                .price(price)
                .type(type)
                .build();
    }

    @Test
    void getOrderOrCreate_빈_Order생성() {
        //given
        int productCount = 1;
        Klass product = new Klass(name, price);
        String type = ProductType.KLASS.name();

        productRepository.save(product);

        //when
        Order when = orderHistory.getOrderOrCreate(product);

        //then
        assertEquals(product.getId(), when.getId());
        assertEquals(product.getName(), when.getName());
        assertEquals(0, when.getPrice());
        assertEquals(0, when.getQuantity());
        assertEquals(type, when.getType());
    }

    @Test
    void addOrder() {
        //given
        Klass product = new Klass(name, price);
        String type = ProductType.KLASS.name();

        productRepository.save(product);

        //when
        boolean when = orderHistory.addOrder(product, 1);

        //then
        assertTrue(when);
    }

    @Test
    void addOrder_금액_테스트() {
        //given
        Klass product = new Klass(name, price);
        String type = ProductType.KLASS.name();

        productRepository.save(product);

        //when
        boolean when = orderHistory.addOrder(product, 5);

        //then
        assertEquals(10000 * 5, orderHistory.getOrderAmount());
    }

    @Test
    void 리스트에_이미_존재하는_Klass_addOrder() {
        //given
        Product product = (Product) productRepository.findById(16374).get();

        orderHistory.addOrder(product, 1);

        //when
        boolean when =  orderHistory.addOrder(product, 1);

        //then
        assertFalse(when);
    }

    @Test
    void OrderList_안에_이미_존재하는_getOrderOrCreate() {
        //given
        int productCount = 1;

        Kit product = (Kit) productRepository.findById(91008).get();
        orderHistory.addOrder(product, productCount);

        //when
        Order when = orderHistory.getOrderOrCreate(product);

        //then
        assertEquals(product.getId(), when.getId());
        assertEquals(product.getName(), when.getName());
        assertEquals(product.getPrice() * productCount, when.getPrice());
        assertEquals(productCount, when.getQuantity());
        assertEquals(product.getType(), when.getType());
    }
}