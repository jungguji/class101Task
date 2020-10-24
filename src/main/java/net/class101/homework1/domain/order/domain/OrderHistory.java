package net.class101.homework1.domain.order.domain;

import lombok.Getter;
import net.class101.homework1.domain.model.ProductType;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderHistory {
    private List<Order> orders = new ArrayList<>();
    private Integer orderAmount;
    private Integer shippingFee;

    public OrderHistory() {
        this.orderAmount = 0;
        this.shippingFee = 5000;
    }

    public void clear() {
        this.orderAmount = 0;
        this.shippingFee = 5000;
    }

    public boolean addOrder(Order order) {
        for (Order o : this.orders) {
            if (order.getId().equals(o.getId()) && ProductType.KLASS.name().equals(order.getType())) {
                return false;
            }
        }

        this.orderAmount += order.getQuantity() * order.getPrice();
        this.shippingFee = this.orderAmount < 50000 ? 5000 : 0;

        return this.orders.add(order);
    }

    public Order getOrderOrCreate(Integer id, String name, String type) {
        Order order = Order.builder()
                .id(id)
                .name(name)
                .price(0)
                .quantity(0)
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
