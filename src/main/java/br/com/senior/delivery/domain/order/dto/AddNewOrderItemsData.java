package br.com.senior.delivery.domain.order.dto;

import java.util.List;

public record AddNewOrderItemsData(
        List<CreateOrderItemData> orderItems
) {
}
