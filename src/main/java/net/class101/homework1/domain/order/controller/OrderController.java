package net.class101.homework1.domain.order.controller;

import net.class101.homework1.domain.order.domain.OrderHistory;
import net.class101.homework1.domain.product.domain.Product;
import net.class101.homework1.global.common.Domain;
import net.class101.homework1.global.common.Response;
import net.class101.homework1.global.common.StatusCode;
import net.class101.homework1.global.util.PropertiesUtil;

public class OrderController {
    public static final String EXIT = "q";
    public static final String ORDER = "o";

    public Response orderOrQuit(String keyIn) {
        if (EXIT.equals(keyIn)) {
            String message = PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.order.quit.message.quit");
            return new Response(StatusCode.EXIT.getCode(), message, null);
        }

        if (!ORDER.equals(keyIn)) {
            String message = PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.order.quit.message.error");
            return new Response(StatusCode.BAD_REQUEST.getCode(), message, null);
        }

        return new Response().ok(null);
    }

    public Response addOrder(OrderHistory orderHistory, Product product, Integer count) {
        if (!orderHistory.addOrder(product, count)) {
            String message = PropertiesUtil.getMessage(Domain.ORDER.getDomain(), "order.klass.duplicate.message.error");
            return new Response(StatusCode.BAD_REQUEST.getCode(), message, null);
        }

        return new Response().ok(null);
    }
}
