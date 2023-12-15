package br.com.senior.burger_place.domain.order.dto;

import br.com.senior.burger_place.domain.order.PaymentForm;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderData(
        @NotNull
        PaymentForm paymentForm,
        @NotNull
        Long clientId,
        @NotEmpty
        List<CreateOrderItemData> orderItems
) {
}
