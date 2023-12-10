package br.com.senior.delivery.domain.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CustomerRegistrationData(
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String cpf,
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String postalCode,
        @NotNull
        int residentialNumber,
        @NotBlank
        String complement
) {
}
