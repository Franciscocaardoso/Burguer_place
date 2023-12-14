package br.com.senior.delivery.domain.customer.dto;

import br.com.senior.delivery.domain.address.AdressData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerRegistrationData(
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String cpf,
        @NotNull
        @Valid
        AdressData address,

        boolean active

) {
}
