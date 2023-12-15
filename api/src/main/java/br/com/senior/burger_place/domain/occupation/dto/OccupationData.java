package br.com.senior.burger_place.domain.occupation.dto;

import br.com.senior.burger_place.domain.occupation.Occupation;
import br.com.senior.burger_place.domain.occupation.PaymentForm;

import java.time.LocalDateTime;
import java.util.List;

public record OccupationData(
        Long id,
        LocalDateTime openedAt,
        LocalDateTime closedAt,
        PaymentForm paymentForm,
        Integer peopleCount,
        OccupationBoardDTO board,
        List<OrderItemData> orderItems
) {
    public OccupationData(Occupation occupation) {
        this(
                occupation.getId(),
                occupation.getBeginOccupation(),
                occupation.getEndOccupation(),
                occupation.getPaymentForm(),
                occupation.getPeopleCount(),
                new OccupationBoardDTO(occupation.getBoard()),
                occupation.getOrderItems().stream().map(OrderItemData::new).toList()
        );
    }
}
