package br.com.senior.delivery.domain.order.dto;

import br.com.senior.delivery.domain.customer.Customer;
import br.com.senior.delivery.domain.order.Order;

public record CustomerOrderData(
        Long id,
        String name
) {
    public CustomerOrderData(Customer customer) {
        this(
                customer.getId(),
                customer.getName()
        );
    }
}
