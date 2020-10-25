package net.class101.homework1.domain.order.domain;

import lombok.RequiredArgsConstructor;
import net.class101.homework1.domain.model.Printer;
import net.class101.homework1.global.common.Domain;
import net.class101.homework1.global.util.PropertiesUtil;

import java.util.List;

@RequiredArgsConstructor
public class OrderHistoryPrinter implements Printer {

    private final OrderHistory orderHistory;

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
                .append((this.orderHistory.getOrderAmount() + this.orderHistory.getShippingFee()))
                .append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.money.suffix")).append("\n");
        sb.append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.menu.border"));

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
        sb.append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.menu.order.amount"))
                .append(orderAmount)
                .append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.money.suffix"));
        if (shippingFee != 0) {
            sb.append("\n");
            sb.append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.menu.shipping.fee"))
                    .append(shippingFee)
                    .append(PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.money.suffix"));
        }

        return sb.toString();
    }
}
