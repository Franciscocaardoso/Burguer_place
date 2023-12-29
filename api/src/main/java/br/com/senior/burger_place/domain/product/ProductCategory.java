package br.com.senior.burger_place.domain.product;

import lombok.Getter;

@Getter
public enum ProductCategory {
    BURGER("Hamburguer"),
    DRINK("Bebidas"),
    ENTRY("Entradas"),
    DESSERT("Sobremesas"),
    SIDE_DISHES("Acompanhamentos");

    private final String text;

    ProductCategory(String text) {
        this.text = text;
    }
}
