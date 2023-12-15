package br.com.senior.burger_place.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderItemData(
        @NotNull
        Long productId,
        @NotNull
        @Positive
        Integer amount
) {
}