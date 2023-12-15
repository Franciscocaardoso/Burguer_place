package br.com.senior.burger_place.domain.occupation.dto;

import br.com.senior.burger_place.domain.customer.Customer;

public record CustomerOccupationData(
        Long id,
        String name
) {
    public CustomerOccupationData(Customer customer) {
        this(
                customer.getId(),
                customer.getName()
        );
    }
}
