package net.class101.homework1.domain.order;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory {
    List<Order> orders = new ArrayList<>();
    Integer orderPrice;
    Integer shippingFee;

    public OrderHistory() {
        this.orderPrice = 0;
        for (Order o : this.orders) {
            this.orderPrice += o.getAmount() * o.getPrice();
        }

        this.shippingFee = getShippingFee();
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    private int getShippingFee() {
        return this.orderPrice < 50000 ? 5000 : 0;
    }

    public Order getOrder(Integer id, String name) {
        Order order = Order.builder()
                .id(id)
                .name(name)
                .amount(0)
                .price(0)
                .build();

        for (Order o : this.orders) {
            if (id.equals(o.getId())) {
                order = o;
                break;
            }
        }

        return order;
    }
}
