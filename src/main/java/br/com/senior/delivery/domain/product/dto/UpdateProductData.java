package br.com.senior.delivery.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateProductData(
        @NotBlank
        String name,
        @NotNull
        @Positive
        Double price,
        String description
) {
}
