package br.com.senior.delivery.domain.customer.dto;

import br.com.senior.delivery.domain.address.Address;
import br.com.senior.delivery.domain.customer.Customer;

public record listingDataCustomers(
        String name,
        String email,
        Address address

) {
    public listingDataCustomers(Customer customer) {
        this(customer.getName(), customer.getEmail(), customer.getAddress());
    }

}
