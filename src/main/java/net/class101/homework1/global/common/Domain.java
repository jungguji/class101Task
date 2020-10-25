package net.class101.homework1.global.common;

import lombok.Getter;

@Getter
public enum Domain {
    MAIN("main"),
    PRODUCT("product"),
    ORDER("order");

    private String domain;

    Domain(String domain) {
        this.domain = domain;
    }
}
