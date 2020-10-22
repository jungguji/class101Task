package net.class101.homework1.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {


    @BeforeEach
    void setUp() {
    }

    @Test
    void add() {
        //given
        Integer price = 100000;
        Integer amount = 1;

        Order order = Order.builder()
                .id(1)
                .name("취요남 요리교실")
                .price(price)
                .amount(amount)
                .build();

        Integer plusAmount = 100;
        //when
        order.add(plusAmount, price);

        //then
        assertEquals( (price * amount) + (price * plusAmount), order.getPrice());
    }
}