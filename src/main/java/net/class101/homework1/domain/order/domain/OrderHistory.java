package net.class101.homework1.domain.order.domain;

import lombok.Getter;
import net.class101.homework1.domain.model.ProductType;
import net.class101.homework1.domain.product.domain.Product;

import java.util.Stack;

@Getter
public class OrderHistory {
    private final Stack<Order> orders = new Stack<>();
    private Integer orderAmount;
    private Integer shippingFee;

    public OrderHistory() {
        this.orderAmount = 0;
        this.shippingFee = 0;
    }

    public void clear() {
        this.orderAmount = 0;
        this.shippingFee = 0;
    }

    public boolean addOrder(Product product, Integer count) {
        Order order = getOrderOrCreate(product);
        order.add(count, product.getPrice());

        if (isDuplicateKlass(order)) {
            return false;
        }

        this.orderAmount += order.getPrice();
        this.shippingFee = this.orderAmount < 50000 ? 5000 : 0;

        return this.orders.add(order);
    }

    private boolean isDuplicateKlass(Order order) {
        boolean isDuplicate = false;
        for (Order o : this.orders) {
            if (order.getId().equals(o.getId()) && ProductType.KLASS.name().equals(order.getType())) {
                isDuplicate = true;
            }
        }

        if (ProductType.KLASS.name().equals(order.getType()) && order.getQuantity() > 1) {
            isDuplicate = true;
        }

        return isDuplicate;
    }

    public Order getOrderOrCreate(Product product) {
        int id = product.getId();
        Order order = Order.builder()
                .id(id)
                .name(product.getName())
                .price(0)
                .quantity(0)
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
