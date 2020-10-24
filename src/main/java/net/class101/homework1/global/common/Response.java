package net.class101.homework1.global.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Response {
    private Integer code;
    private String message;
    private Object body;

    public Response(Integer code, String message, Object body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public Response ok(Object body) {
        this.code = StatusCode.OK.getCode();
        this.message = "OK";

        return new Response(code, message, body);
    }
}
