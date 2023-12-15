package br.com.senior.burger_place.domain.board;

import jakarta.validation.constraints.NotNull;

public record BoardRegisterData (
        @NotNull
        int number,
        @NotNull
        int capacity,
        @NotNull
        Localizacao localizacao
){
}
