package br.com.senior.burger_place.utils;

import br.com.senior.burger_place.domain.product.Product;
import br.com.senior.burger_place.domain.product.ProductCategory;
import br.com.senior.burger_place.domain.product.dto.CreateProductDTO;
import br.com.senior.burger_place.domain.product.dto.ProductDTO;
import br.com.senior.burger_place.domain.product.dto.UpdateProductDTO;

public class ProductTestFactory {

    public static Product productFactory(Long id) {
        return new Product(
                id,
                "Hamburguer clássico",
                45.8,
                "Carne, tomate, pepino, bacon, alface, maionese",
                ProductCategory.BURGER,
                null,
                true
        );
    }

    public static CreateProductDTO createProductDTOFactory(String name, Double price) {
        return new CreateProductDTO(name, price, null);
    }

    public static UpdateProductDTO updateProductDTOFactory(String name, Double price) {
        return new UpdateProductDTO(
                name,
                price,
                "Carne, tomate, pepino, bacon, alface, maionese"
        );
    }

    public static ProductDTO productDTOFactory(Long id) {
        return ProductTestFactory.productDTOFactory(
                id,
                "Hamburguer tradicional",
                25.9,
                "Carne, tomate, pepino, bacon, alface, maionese",
                ProductCategory.BURGER,
                null
        );
    }

    public static ProductDTO productDTOFactory(
            Long id,
            String name,
            Double price,
            String ingredients,
            ProductCategory category,
            String URL
    ) {
        return new ProductDTO(
                id,
                name,
                price,
                ingredients,
                category,
                URL
        );
    }

}
