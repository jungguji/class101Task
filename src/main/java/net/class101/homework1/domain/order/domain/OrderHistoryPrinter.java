package net.class101.homework1.domain.order.domain;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.domain.model.Printer;

import java.util.List;

@RequiredArgsConstructor
public class OrderHistoryPrinter implements Printer{

    private final OrderHistory orderHistory;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("주문 내역:").append("\n");
        sb.append("--------------------------").append("\n");
        sb.append(getOrderPrint(this.orderHistory.getOrders())).append("\n");
        sb.append("--------------------------").append("\n");
        sb.append(getOrderAmount(this.orderHistory.getOrderAmount(), this.orderHistory.getShippingFee())).append("\n");
        sb.append("--------------------------").append("\n");
        sb.append("지불 금액: " + (this.orderHistory.getOrderAmount() + this.orderHistory.getShippingFee())).append("\n");
        sb.append("--------------------------").append("\n");

        return sb.toString();
    }

    private String getOrderPrint(List<Order> orders) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Order order : orders) {
            if (!isFirst) {
                sb.append("\n");
            }

            sb.append(order.getName() + " - " + order.getQuantity() + "개");
            isFirst = false;
        }

        return sb.toString();
    }

    private String getOrderAmount(int orderAmount, int shippingFee) {
        StringBuilder sb = new StringBuilder();
        sb.append("주문 금액: " + orderAmount + "원");
        if (shippingFee != 0) {
            sb.append("\n");
            sb.append("배송비: " + shippingFee + "원");
        }

        return sb.toString();
    }
}
