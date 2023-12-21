package br.com.senior.burger_place.domain.customer.dto;

import br.com.senior.burger_place.domain.address.Address;
import br.com.senior.burger_place.domain.customer.Customer;

public record listingCustomersDTO(
        String name,
        String email,
        Address address

) {
    public listingCustomersDTO(Customer customer) {
        this(customer.getName(), customer.getEmail(), customer.getAddress());
    }

}
