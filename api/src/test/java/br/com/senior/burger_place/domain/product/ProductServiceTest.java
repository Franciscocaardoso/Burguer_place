package br.com.senior.burger_place.domain.product;

import br.com.senior.burger_place.domain.product.dto.CreateProductDTO;
import br.com.senior.burger_place.domain.product.dto.ProductDTO;
import br.com.senior.burger_place.domain.product.dto.UpdateProductData;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    void listProducts_whenExistProducts_shouldReturnPageWithProducts() {
        List<Product> someProducts = Arrays.asList(
                new Product(1L, "Hamburguer clássico", 45.8, null, true),
                new Product(2L, "Hamburguer clássico americano", 42.8, null, true)
        );

        Page<Product> somePage = new PageImpl<>(someProducts);

        when(this.productRepository.findAllByActiveTrue(any(Pageable.class))).thenReturn(somePage);

        List<ProductDTO> output = this.productService.listProducts(Pageable.ofSize(20)).toList();

        assertAll(
                () -> assertEquals(someProducts.size(), output.size()),
                () -> assertEquals(output.get(0).id(), someProducts.get(0).getId()),
                () -> assertEquals(output.get(0).name(), someProducts.get(0).getName()),
                () -> assertEquals(output.get(0).description(), someProducts.get(0).getDescription()),
                () -> assertEquals(output.get(0).price(), someProducts.get(0).getPrice()),
                () -> assertEquals(output.get(1).id(), someProducts.get(1).getId()),
                () -> assertEquals(output.get(1).name(), someProducts.get(1).getName()),
                () -> assertEquals(output.get(1).description(), someProducts.get(1).getDescription()),
                () -> assertEquals(output.get(1).price(), someProducts.get(1).getPrice())
        );
    }

    @Test
    void listProducts_whenExistProducts_shouldReturnPageWithoutProducts() {
        Page<Product> someEmptyPage = new PageImpl<>(new ArrayList<>());

        when(this.productRepository.findAllByActiveTrue(any(Pageable.class))).thenReturn(someEmptyPage);

        List<ProductDTO> output = this.productService.listProducts(Pageable.ofSize(20)).toList();

        assertTrue(output.isEmpty());
    }

    @Test
    void showProduct_whenProductExists_shouldReturnAnOptionalWithProduct() {
        Product someProduct = new Product(1L, "Hamburguer clássico", 45.8, null, true);

        when(this.productRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(someProduct);

        Optional<ProductDTO> output = this.productService.showProduct(1L);

        assertAll(
                () -> assertFalse(output.isEmpty()),
                () -> assertEquals(output.get().id(), someProduct.getId()),
                () -> assertEquals(output.get().name(), someProduct.getName()),
                () -> assertEquals(output.get().description(), someProduct.getDescription()),
                () -> assertEquals(output.get().price(), someProduct.getPrice())
        );
    }

    @Test
    void showProduct_whenProductDoesNotExist_shouldReturnAnEmptyOptional() {
        when(this.productRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(null);

        Optional<ProductDTO> output = this.productService.showProduct(1L);

        assertTrue(output.isEmpty());
    }

    @Test
    void showProduct_whenIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.showProduct(null)
        );

        assertEquals("ID não pode ser null", exception.getMessage());
    }

    @ParameterizedTest()
    @ValueSource(longs = {0L, -1L})
    void showProduct_whenIdLessThanOrEqualToZero_shouldThrow(Long id) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.showProduct(id)
        );

        assertEquals("ID inválida", exception.getMessage());
    }

    @Test
    void createProduct_whenDTOIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.createProduct(null)
        );

        assertEquals("DTO não pode ser null", exception.getMessage());
    }

    @Test
    void createProduct_whenDTONameIsNull_shouldThrow() {
        CreateProductDTO input = new CreateProductDTO(null, 10.5, null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.createProduct(input)
        );

        assertEquals("nome inválido", exception.getMessage());
    }

    @Test
    void createProduct_whenDTOPriceIsNull_shouldThrow() {
        CreateProductDTO input = new CreateProductDTO("Hamburguer tradicional", null, null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.createProduct(input)
        );

        assertEquals("preço inválido", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0D, -1D, -10D})
    void createProduct_whenDTOPriceIsLessThanOrEqualToZero_shouldThrow(Double price) {
        CreateProductDTO input = new CreateProductDTO("Hamburguer tradicional", price, null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.createProduct(input)
        );

        assertEquals("preço inválido", exception.getMessage());
    }

    @Test
    void createProduct_whenDTOIsValid_shouldSaveAndReturnProductDTO() {
        Product product = new Product(1L, "Hamburguer tradicional", 10.2D, null, true);
        CreateProductDTO input = new CreateProductDTO(product.getName(), product.getPrice(), product.getDescription());

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);

        when(this.productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO output = this.productService.createProduct(input);

        verify(this.productRepository, atMostOnce()).save(any(Product.class));

        verify(this.productRepository).save(argumentCaptor.capture());
        Product capturedValue = argumentCaptor.getValue();

        assertEquals(input.name(), capturedValue.getName());
        assertEquals(input.description(), capturedValue.getDescription());
        assertEquals(input.price(), capturedValue.getPrice());

        assertEquals(product.getId(), output.id());
        assertEquals(product.getName(), output.name());
        assertEquals(product.getDescription(), output.description());
        assertEquals(product.getPrice(), output.price());
    }

    @Test
    void updateProduct_whenIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.updateProduct(null, null)
        );

        assertEquals("ID inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void updateProduct_whenIdIsLessThanOrEqualsToZero_shouldThrow(Long id) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.updateProduct(id, null)
        );

        assertEquals("ID inválida", exception.getMessage());
    }

    @Test
    void updateProduct_whenDTOIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.updateProduct(1L, null)
        );

        assertEquals("DTO não pode ser null", exception.getMessage());
    }

    @Test
    void updateProduct_whenDTONameIsNull_shouldThrow() {
        UpdateProductData input = new UpdateProductData(null, null, null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.updateProduct(1L, input)
        );

        assertEquals("nome inválido", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void updateProduct_whenDTONameIsEmpty_shouldThrow(String name) {
        UpdateProductData input = new UpdateProductData(name, null, null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.updateProduct(1L, input)
        );

        assertEquals("nome inválido", exception.getMessage());
    }

    @Test
    void updateProduct_whenDTOPriceIsNull_shouldThrow() {
        UpdateProductData input = new UpdateProductData("Hamburguer duplo", null, null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.updateProduct(1L, input)
        );

        assertEquals("preço inválido", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0D, -1D, -10D})
    void updateProduct_whenDTOPriceIsLessThanOrEqualToZero_shouldThrow(Double price) {
        UpdateProductData input = new UpdateProductData("Hamburguer duplo", price, null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.updateProduct(1L, input)
        );

        assertEquals("preço inválido", exception.getMessage());
    }

    @Test
    void updateProduct_whenProductDoesNotExist_shouldThrow() {
        UpdateProductData input = new UpdateProductData("Hamburguer duplo", 10.5, null);

        when(this.productRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(null);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.productService.updateProduct(1L, input)
        );

        assertEquals("Produto não existe ou foi inativado", exception.getMessage());
    }

    @Test
    void updateProduct_whenProductExists_shouldUpdateAndReturnProductDTO() {
        Product product = new Product(1L, "Hamburguer duplo", 10.5, null, true);
        UpdateProductData input = new UpdateProductData(product.getName(), product.getPrice(), product.getDescription());

        Product productSpy = spy(product);

        when(this.productRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(productSpy);

        ProductDTO output = this.productService.updateProduct(1L, input);

        verify(productSpy, atMostOnce()).update(any(UpdateProductData.class));
        assertAll(
                () -> assertEquals(product.getId(), output.id()),
                () -> assertEquals(product.getName(), output.name()),
                () -> assertEquals(product.getDescription(), output.description()),
                () -> assertEquals(product.getPrice(), output.price())
        );
    }

    @Test
    void deleteProduct_whenIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.deleteProduct(null)
        );

        assertEquals("ID inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void deleteProduct_whenIdIsLessThanOrEqualsToZero_shouldThrow(Long id) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.productService.deleteProduct(id)
        );

        assertEquals("ID inválida", exception.getMessage());
    }

    @Test
    void deleteProduct_whenProductDoesNotExist_shouldThrow() {
        when(this.productRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(null);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.productService.deleteProduct(1L)
        );

        assertEquals("Produto não existe ou foi inativado", exception.getMessage());
    }

    @Test
    void deleteProduct_whenProductExists_shouldInactivateProduct() {
        Product productSpy = spy(new Product(1L, "Hamburguer tradicional", 10.5, null, true));
        when(this.productRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(productSpy);

        this.productService.deleteProduct(1L);

        verify(productSpy, atMostOnce()).inactivate();
        assertFalse(productSpy.isActive());
    }
}
