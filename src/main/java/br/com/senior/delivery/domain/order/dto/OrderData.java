package br.com.senior.delivery.domain.order.dto;

import br.com.senior.delivery.domain.order.Order;

import java.time.LocalDateTime;

public record OrderData(
        Long id,
        LocalDateTime openedAt,
        LocalDateTime closedAt,
        String paymentForm,
        String status
) {
    public OrderData(Order order) {
        this(
                order.getId(),
                order.getOpenedAt(),
                order.getClosedAt(),
                order.getPaymentForm().name(),
                order.getStatus().name()
        );

    }
}
