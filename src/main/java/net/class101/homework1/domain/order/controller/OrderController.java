package net.class101.homework1.domain.order.controller;

import net.class101.homework1.global.common.Response;
import net.class101.homework1.global.common.StatusCode;

public class OrderController {
    public static final String EXIT = "q";
    public static final String ORDER = "o";

    public Response orderOrQuit(String keyIn) {
        if (EXIT.equals(keyIn)) {
            return new Response(StatusCode.EXIT.getCode(), "고객님의 주문 감사합니다.", null);
        }

        if (!ORDER.equals(keyIn)) {
            return new Response(StatusCode.BAD_REQUEST.getCode(), "잘못된 입력입니다.", null);
        }

        return new Response().ok(null);
    }
}
