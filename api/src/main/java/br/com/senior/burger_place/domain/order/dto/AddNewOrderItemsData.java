package br.com.senior.burger_place.domain.order.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AddNewOrderItemsData(
        @NotEmpty
        List<CreateOrderItemData> orderItems
) {
}
