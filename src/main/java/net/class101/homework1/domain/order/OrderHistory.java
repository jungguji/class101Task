package net.class101.homework1.domain.order;

import net.class101.homework1.domain.model.ProductType;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory {
    private List<Order> orders = new ArrayList<>();
    private Integer orderAmount;
    private Integer shippingFee;

    public OrderHistory() {
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

    public void print() {
        System.out.println("--------------------------");
        System.out.println(getOrderPrint());
        System.out.println("--------------------------");
        System.out.println(getOrderAmount());
        System.out.println("--------------------------");
        System.out.println("지불 금액: " + (this.orderAmount + this.shippingFee));
        System.out.println("--------------------------");
    }

    private String getOrderPrint() {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Order order : this.orders) {
            if (!isFirst) {
                sb.append("\n");
            }

            sb.append(order.getName() + " - " + order.getQuantity() + "개");
            isFirst = false;
        }

        return sb.toString();
    }

    private String getOrderAmount() {
        StringBuilder sb = new StringBuilder();
        sb.append("주문 금액: " + this.orderAmount + "원");
        if (this.shippingFee != 0) {
            sb.append("\n배송비: " + this.shippingFee + "원");
        }

        return sb.toString();
    }
}
