package br.com.senior.burger_place.domain.order.dto;

import br.com.senior.burger_place.domain.customer.Customer;

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
