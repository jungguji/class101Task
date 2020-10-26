package net.class101.homework1.domain.order.domain;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.domain.model.Printer;
import net.class101.homework1.global.common.Domain;
import net.class101.homework1.global.util.PropertiesUtil;

import java.text.DecimalFormat;
import java.util.Stack;

@RequiredArgsConstructor
public class OrderHistoryPrinter implements Printer {

    private final OrderHistory orderHistory;
    DecimalFormat formatter = new DecimalFormat("#,###");

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.menu.order.history")).append("\n");
        sb.append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.menu.border"));
        sb.append(getOrderPrint(this.orderHistory.getOrders())).append("\n");
        sb.append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.menu.border"));
        sb.append(getOrderAmount(this.orderHistory.getOrderAmount(), this.orderHistory.getShippingFee())).append("\n");
        sb.append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.menu.border"));
        sb.append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.menu.payment"))
                .append(formatter.format(this.orderHistory.getOrderAmount() + this.orderHistory.getShippingFee()))
                .append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.money.suffix")).append("\n");
        sb.append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.menu.border"));

        return sb.toString();
    }

    private String getOrderPrint(Stack<Order> orders) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        while (!orders.isEmpty()) {
            Order order = orders.pop();

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
        sb.append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.menu.order.amount"))
                .append(formatter.format(orderAmount))
                .append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.money.suffix"));
        if (shippingFee != 0) {
            sb.append("\n");
            sb.append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.menu.shipping.fee"))
                    .append(formatter.format(shippingFee))
                    .append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.money.suffix"));
        }

        return sb.toString();
    }
}
