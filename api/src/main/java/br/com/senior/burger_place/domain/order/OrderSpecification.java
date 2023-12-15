package br.com.senior.burger_place.domain.order;

import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

    public static Specification<Order> filterByOrderStatus(OrderStatus orderStatusParam) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), orderStatusParam);
    }

}
