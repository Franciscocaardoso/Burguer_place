package br.com.senior.delivery.domain.product.dto;

import br.com.senior.delivery.domain.product.Product;

public record ProductData(
        Long id,
        String name,
        Double price,
        String description
) {
    public ProductData(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription()
        );
    }
}
