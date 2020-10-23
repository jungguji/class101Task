package net.class101.homework1.domain.order;

import net.class101.homework1.domain.model.ProductType;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory {
    private List<Order> orders = new ArrayList<>();
    private Integer orderPrice;
    private Integer shippingFee;

    public OrderHistory() {
        this.orderPrice = 0;
        for (Order o : this.orders) {
            this.orderPrice += o.getAmount() * o.getPrice();
        }

        this.shippingFee = getShippingFee();
    }

    public boolean addOrder(Order order) {
        for (Order o : this.orders) {
            if (order.getId().equals(o.getId()) && ProductType.KLASS.equals(order.getType())) {
                return false;
            }
        }
        return this.orders.add(order);
    }

    private int getShippingFee() {
        return this.orderPrice < 50000 ? 5000 : 0;
    }

    public Order getOrderOrCreate(Integer id, String name, ProductType type) {
        Order order = Order.builder()
                .id(id)
                .name(name)
                .price(0)
                .amount(0)
                .type(type)
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
