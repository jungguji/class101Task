package net.class101.homework1.domain.order;

import net.class101.homework1.domain.model.ProductType;
import net.class101.homework1.domain.order.domain.Order;
import net.class101.homework1.domain.order.domain.OrderHistory;
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
    void getOrderOrCreate() {
        //given
        String type = ProductType.KLASS.name();

        //when
        Order when = orderHistory.getOrderOrCreate(id,name, type);

        //then
        assertEquals(id, when.getId());
        assertEquals(name, when.getName());
        assertEquals(0, when.getPrice());
        assertEquals(0, when.getQuantity());
        assertEquals(type, when.getType());
    }

    @Test
    void addOrder() {
        //given
        String type = ProductType.KLASS.name();
        Order order = createOrder(type);

        //when
        boolean when = orderHistory.addOrder(order);

        //then
        assertTrue(when);
    }

    @Test
    void 리스트에_이미_존재하는_Klass_addOrder() {
        //given
        Order order1 = createOrder(ProductType.KLASS.name());
        Order order2 = Order.builder()
                .id(2)
                .name(name)
                .quantity(amount)
                .price(price)
                .type(ProductType.KIT.name())
                .build();
        Order order3 = createOrder(ProductType.KLASS.name());

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
        String type = ProductType.KLASS.name();
        Order order = createOrder(type);
        orderHistory.addOrder(order);

        //when
        Order when = orderHistory.getOrderOrCreate(id,name, type);

        //then
        assertEquals(id, when.getId());
        assertEquals(name, when.getName());
        assertEquals(amount * price, when.getPrice());
        assertEquals(amount, when.getQuantity());
        assertEquals(type, when.getType());
    }
}