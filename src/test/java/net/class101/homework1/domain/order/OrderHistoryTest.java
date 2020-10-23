package net.class101.homework1.domain.order;

import net.class101.homework1.domain.model.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderHistoryTest {

    private static final int id = 1;
    private static final String name = "test";
    private static final int amount = 100;
    private static final int price = 10000;

    OrderHistory orderHistory;

    @BeforeEach
    void setUp() {
         orderHistory = new OrderHistory();
    }

    private Order createOrder(ProductType type) {
        return Order.builder()
                .id(id)
                .name(name)
                .amount(amount)
                .price(price)
                .type(type)
                .build();
    }
    @Test
    void getOrderOrCreate() {
        //given
        ProductType type = ProductType.KLASS;

        //when
        Order when = orderHistory.getOrderOrCreate(id,name, type);

        //then
        assertEquals(id, when.getId());
        assertEquals(name, when.getName());
        assertEquals(0, when.getPrice());
        assertEquals(0, when.getAmount());
        assertEquals(type, when.getType());
    }

    @Test
    void addOrder() {
        //given
        ProductType type = ProductType.KLASS;
        Order order = createOrder(type);

        //when
        boolean when = orderHistory.addOrder(order);

        //then
        assertTrue(when);
    }

    @Test
    void 리스트에_이미_존재하는_Klass_addOrder() {
        //given
        Order order1 = createOrder(ProductType.KLASS);
        Order order2 = Order.builder()
                .id(2)
                .name(name)
                .amount(amount)
                .price(price)
                .type(ProductType.KIT)
                .build();
        Order order3 = createOrder(ProductType.KLASS);

        orderHistory.addOrder(order1);
        orderHistory.addOrder(order2);

        //when
        boolean when =  orderHistory.addOrder(order3);

        //then
        assertFalse(when);
    }

    @Test
    void OrderList_안에_있는_getOrderOrCreate() {
        //given
        ProductType type = ProductType.KLASS;
        Order order = createOrder(type);
        orderHistory.addOrder(order);

        //when
        Order when = orderHistory.getOrderOrCreate(id,name, type);

        //then
        assertEquals(id, when.getId());
        assertEquals(name, when.getName());
        assertEquals(amount * price, when.getPrice());
        assertEquals(amount, when.getAmount());
        assertEquals(type, when.getType());
    }
}