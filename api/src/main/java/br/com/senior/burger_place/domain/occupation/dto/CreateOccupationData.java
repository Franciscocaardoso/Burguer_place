package br.com.senior.burger_place.domain.occupation.dto;

import br.com.senior.burger_place.domain.occupation.PaymentForm;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public record CreateOccupationData(
        @NotNull
        @PastOrPresent
        LocalDateTime beginOccupation,
        @NotNull
        @Positive
        Integer peopleCount,
        @NotNull
        @Positive
        Long boardId

) {
}
