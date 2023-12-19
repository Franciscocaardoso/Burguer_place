package br.com.senior.burger_place.domain.product;

import br.com.senior.burger_place.domain.product.dto.CreateProductDTO;
import br.com.senior.burger_place.domain.product.dto.ProductDTO;
import br.com.senior.burger_place.domain.product.dto.UpdateProductData;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<ProductDTO> listProducts(Pageable pageable) {
        return this.productRepository.findAllByActiveTrue(pageable).map(ProductDTO::new);
    }

    public Optional<ProductDTO> showProduct(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser null");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválida");
        }

        Product product = this.productRepository.getReferenceByIdAndActiveTrue(id);

        if (product == null) {
            return Optional.empty();
        }

        return Optional.of(new ProductDTO(product));
    }

    public ProductDTO createProduct(CreateProductDTO productData) {
        if (productData == null) {
            throw new IllegalArgumentException("DTO não pode ser null");
        }

        if (productData.name() == null) {
            throw new IllegalArgumentException("nome inválido");
        }

        if (productData.price() == null || productData.price() <= 0) {
            throw new IllegalArgumentException("preço inválido");
        }

        Product product = new Product(
                productData.name(),
                productData.price(),
                productData.description()
        );

        return new ProductDTO(this.productRepository.save(product));
    }

    public ProductDTO updateProduct(Long id, UpdateProductData productData) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválida");
        }

        if (productData == null) {
            throw new IllegalArgumentException("DTO não pode ser null");
        }

        if (productData.name() == null || productData.name().trim().isEmpty()) {
            throw new IllegalArgumentException("nome inválido");
        }

        if (productData.price() == null || productData.price() <= 0) {
            throw new IllegalArgumentException("preço inválido");
        }

        Product product = this.productRepository.getReferenceByIdAndActiveTrue(id);

        if (product == null) {
            throw new EntityNotFoundException("Produto não existe ou foi inativado");
        }

        product.update(productData);
        return new ProductDTO(product);
    }

    public void deleteProduct(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválida");
        }

        Product product = this.productRepository.getReferenceByIdAndActiveTrue(id);

        if (product == null) {
            throw new EntityNotFoundException("Produto não existe ou foi inativado");
        }

        product.inactivate();
    }
}
