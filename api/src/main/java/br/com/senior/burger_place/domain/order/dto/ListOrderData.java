package br.com.senior.burger_place.domain.order.dto;

import br.com.senior.burger_place.domain.order.Order;

import java.time.LocalDateTime;

public record ListOrderData(
        Long id,
        LocalDateTime openedAt,
        LocalDateTime closedAt,
        String paymentForm,
        String status
) {
    public ListOrderData(Order order) {
        this(
                order.getId(),
                order.getOpenedAt(),
                order.getClosedAt(),
                order.getPaymentForm().name(),
                order.getStatus().name()
        );
    }
}
