package br.com.senior.delivery.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemData(
        @NotNull
        Long productId,
        @NotNull
        @Positive
        Integer amount
) {
}
