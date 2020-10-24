package net.class101.homework1.global.common;

import lombok.Getter;

@Getter
public enum StatusCode {
    EXIT(0),
    OK(200),
    BAD_REQUEST(400),
    NOT_FOUND(404);

    private int code;

    StatusCode(int code) {
        this.code = code;
    }
}
