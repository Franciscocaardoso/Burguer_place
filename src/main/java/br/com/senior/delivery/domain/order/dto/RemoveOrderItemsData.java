package br.com.senior.delivery.domain.order.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RemoveOrderItemsData(
        @NotEmpty
        List<Long> orderItems
) {
}
