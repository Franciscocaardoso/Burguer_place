package br.com.senior.delivery.domain.order.dto;

import jakarta.validation.constraints.Positive;

public record UpdateOrderItemData(
        @Positive
        Integer amount
) {
}
