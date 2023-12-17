package br.com.senior.burger_place.domain.occupation.dto;

import br.com.senior.burger_place.domain.occupation.Occupation;
import br.com.senior.burger_place.domain.occupation.PaymentForm;

import java.time.LocalDateTime;
import java.util.List;

public record OccupationDTO(
        Long id,
        LocalDateTime beginOccupation,
        LocalDateTime endOccupation,
        PaymentForm paymentForm,
        Integer peopleCount,
        OccupationBoardDTO board,
        List<OrderItemDTO> orderItems
) {
    public OccupationDTO(Occupation occupation) {
        this(
                occupation.getId(),
                occupation.getBeginOccupation(),
                occupation.getEndOccupation(),
                occupation.getPaymentForm(),
                occupation.getPeopleCount(),
                new OccupationBoardDTO(occupation.getBoard()),
                occupation.getOrderItems() != null
                        ? occupation.getOrderItems().stream().map(OrderItemDTO::new).toList()
                        : null
        );
    }
}
