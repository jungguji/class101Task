package net.class101.homework1.domain.product.exception;

public class SoldOutException extends RuntimeException {
    public static final String MESSAGE = "재고가 부족합니다.";

    public SoldOutException() {
        super(MESSAGE);
    }
}