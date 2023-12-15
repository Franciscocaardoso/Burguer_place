package br.com.senior.burger_place.domain.occupation.dto;

import jakarta.validation.constraints.Positive;

public record UpdateOrderItemData(
        @Positive
        Integer amount
) {
}
