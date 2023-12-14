package br.com.senior.delivery.domain.customer.dto;

import br.com.senior.delivery.domain.address.AdressData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CustomerUploadData(
        String name,
        String email,
        @Valid
        AdressData adressData) {
}
