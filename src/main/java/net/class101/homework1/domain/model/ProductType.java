package net.class101.homework1.domain.model;

import lombok.Getter;

@Getter
public enum ProductType {
    KLASS("클래스"),
    KIT("키트");

    private String type;

    ProductType(String type) {
        this.type = type;
    }
}
