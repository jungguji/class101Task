package net.class101.homework1.domain.order.domain;

import lombok.Getter;
import net.class101.homework1.domain.model.ProductType;
import net.class101.homework1.domain.product.domain.Product;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderHistory {
    private final List<Order> orders = new ArrayList<>();
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

    public boolean addOrder(Product product, Integer count) {
        Order order = getOrderOrCreate(product, count);
        order.add(count, product.getPrice());

        for (Order o : this.orders) {
            if (order.getId().equals(o.getId()) && ProductType.KLASS.name().equals(order.getType())) {
                return false;
            }
        }

        this.orderAmount += order.getQuantity() * order.getPrice();
        this.shippingFee = this.orderAmount < 50000 ? 5000 : 0;

        return this.orders.add(order);
    }

    public Order getOrderOrCreate(Product product, Integer count) {
        int id = product.getId();
        Order order = Order.builder()
                .id(id)
                .name(product.getName())
                .price(product.getPrice())
                .quantity(count)
                .type(product.getType())
                .build();

        for (Order o : this.orders) {
            if (id == o.getId()) {
                order = o;
                break;
            }
        }

        return order;
    }
}
