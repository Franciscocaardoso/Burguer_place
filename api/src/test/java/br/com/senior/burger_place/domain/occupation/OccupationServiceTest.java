package br.com.senior.burger_place.domain.occupation;

import br.com.senior.burger_place.domain.board.Board;
import br.com.senior.burger_place.domain.board.BoardLocation;
import br.com.senior.burger_place.domain.board.BoardRepository;
import br.com.senior.burger_place.domain.customer.Customer;
import br.com.senior.burger_place.domain.customer.CustomerRepository;
import br.com.senior.burger_place.domain.occupation.dto.*;
import br.com.senior.burger_place.domain.product.Product;
import br.com.senior.burger_place.domain.product.ProductRepository;
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

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OccupationServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private OccupationRepository occupationRepository;
    @InjectMocks
    private OccupationService occupationService;

    @Test
    void listOccupations_whenExistProducts_shouldReturnPageWithOccupationsDTO() {
        Product someProduct = new Product("Hamburguer tradicional", 10.6, null);
        List<OrderItem> someOrderItems = List.of(
                new OrderItem(2, someProduct.getPrice(), someProduct)
        );
        List<Occupation> someOccupations = Arrays.asList(
                new Occupation(
                        1L,
                        LocalDateTime.now(),
                        null,
                        2,
                        null,
                        null,
                        new Board(1L, 1, 2, BoardLocation.AREA_INTERNA, true),
                        null,
                        true
                ),
                new Occupation(
                        2L,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(20),
                        2,
                        PaymentForm.CARTAO_CREDITO,
                        someOrderItems,
                        new Board(2L, 1, 2, BoardLocation.AREA_INTERNA, true),
                        null,
                        true
                )
        );

        Page<Occupation> somePage = new PageImpl<>(someOccupations);

        when(this.occupationRepository.findAllByActiveTrue(any(Pageable.class))).thenReturn(somePage);

        List<ListOccupationDTO> output = this.occupationService.listOccupations(Pageable.ofSize(20)).toList();

        assertAll(
                () -> assertEquals(someOccupations.size(), output.size()),
                () -> assertEquals(someOccupations.get(0).getId(), output.get(0).id()),
                () -> assertEquals(someOccupations.get(0).getBeginOccupation(), output.get(0).beginOccupation()),
                () -> assertEquals(someOccupations.get(0).getEndOccupation(), output.get(0).endOccupation()),
                () -> assertEquals(someOccupations.get(0).getPeopleCount(), output.get(0).peopleCount()),
                () -> assertEquals(someOccupations.get(0).getPaymentForm(), output.get(0).paymentForm()),
                () -> assertEquals(someOccupations.get(0).getBoard().getNumber(), output.get(0).board().number()),
                () -> assertEquals(someOccupations.get(1).getId(), output.get(1).id()),
                () -> assertEquals(someOccupations.get(1).getBeginOccupation(), output.get(1).beginOccupation()),
                () -> assertEquals(someOccupations.get(1).getEndOccupation(), output.get(1).endOccupation()),
                () -> assertEquals(someOccupations.get(1).getPeopleCount(), output.get(1).peopleCount()),
                () -> assertEquals(someOccupations.get(1).getPaymentForm(), output.get(1).paymentForm()),
                () -> assertEquals(someOccupations.get(1).getBoard().getNumber(), output.get(1).board().number())
        );
    }

    @Test
    void listOccupations_whenProductsDoesNotExist_shouldReturnAnEmptyPage() {
        Page<Occupation> somePage = new PageImpl<>(new ArrayList<>());

        when(this.occupationRepository.findAllByActiveTrue(any(Pageable.class))).thenReturn(somePage);

        List<ListOccupationDTO> output = this.occupationService.listOccupations(Pageable.ofSize(20)).toList();

        assertTrue(output.isEmpty());
    }

    @Test
    void showOccupation_whenIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.showOccupation(null)
        );

        assertEquals("ID inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void showOccupation_whenIdIsLessThanOrEqualToZero_shouldThrow(Long id) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.showOccupation(id)
        );

        assertEquals("ID inválida", exception.getMessage());
    }

    @Test
    void showOccupation_whenProductDoesNotExist_shouldReturnEmptyOptional() {
        when(this.occupationRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(null);

        Optional<OccupationDTO> output = this.occupationService.showOccupation(1L);

        assertTrue(output.isEmpty());
    }

    @Test
    void showOccupation_whenProductExists_shouldReturnAnOptionalWithOccupationDTO() {
        Occupation someOccupation = new Occupation(
                1L,
                LocalDateTime.now(),
                null,
                2,
                null,
                new ArrayList<>(),
                new Board(1L, 1, 2, BoardLocation.AREA_INTERNA, true),
                new HashSet<>(),
                true
        );

        when(this.occupationRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(someOccupation);

        Optional<OccupationDTO> output = this.occupationService.showOccupation(1L);

        assertAll(
                () -> assertFalse(output.isEmpty()),
                () -> assertEquals(someOccupation.getId(), output.get().id()),
                () -> assertEquals(someOccupation.getBeginOccupation(), output.get().beginOccupation()),
                () -> assertEquals(someOccupation.getEndOccupation(), output.get().endOccupation()),
                () -> assertEquals(someOccupation.getOrderItems().size(), output.get().orderItems().size()),
                () -> assertEquals(someOccupation.getCustomers().size(), output.get().customers().size()),
                () -> assertEquals(someOccupation.getPaymentForm(), output.get().paymentForm()),
                () -> assertEquals(someOccupation.getBoard().getNumber(), output.get().board().number()),
                () -> assertEquals(someOccupation.getBoard().getLocation(), output.get().board().location())
        );

    }

    @Test
    void createOccupation_whenDTOIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.createOccupation(null)
        );

        assertEquals("DTO inválido", exception.getMessage());
    }

    @Test
    void createOccupation_whenDTOBoardIdIsNull_shouldThrow() {
        CreateOccupationDTO input = new CreateOccupationDTO(
                LocalDateTime.now().minusMinutes(10),
                2,
                null,
                null
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.createOccupation(input)
        );

        assertEquals("ID da mesa é inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void createOccupation_whenDTOBoardIdIsLessThanOrEqualToZero_shouldThrow(Long id) {
        CreateOccupationDTO input = new CreateOccupationDTO(
                LocalDateTime.now().minusMinutes(10),
                2,
                id,
                null
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.createOccupation(input)
        );

        assertEquals("ID da mesa é inválida", exception.getMessage());
    }

    @Test
    void createOccupation_whenDTOBeginOccupationIsNull_shouldThrow() {
        CreateOccupationDTO input = new CreateOccupationDTO(
                null,
                2,
                1L,
                null
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.createOccupation(input)
        );

        assertEquals("Data de início da ocupação é inválida", exception.getMessage());
    }

    @Test
    void createOccupation_whenDTOBeginOccupationIsAfterNow_shouldThrow() {
        CreateOccupationDTO input = new CreateOccupationDTO(
                LocalDateTime.now().plusMinutes(10),
                2,
                1L,
                null
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.createOccupation(input)
        );

        assertEquals("A data de entrada deve ser menor ou igual a atual", exception.getMessage());
    }

    @Test
    void createOccupation_whenBoardDoesNotExist_shouldThrow() {
        CreateOccupationDTO input = new CreateOccupationDTO(
                LocalDateTime.now().minusMinutes(10),
                2,
                1L,
                null
        );

        when(this.boardRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(null);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.createOccupation(input)
        );

        assertEquals("Mesa não existe ou foi inativada", exception.getMessage());
    }

    @Test
    void createOccupation_whenBoardIsOccupied_shouldThrow() {
        CreateOccupationDTO input = new CreateOccupationDTO(
                LocalDateTime.now().minusMinutes(10),
                2,
                1L,
                null
        );

        Board board = new Board(1L, 1, 2, BoardLocation.AREA_INTERNA, true);

        when(this.boardRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(board);
        when(this.boardRepository.isBoardOccupied(anyLong())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.createOccupation(input)
        );

        assertEquals("Mesa já está ocupada", exception.getMessage());
    }

    @Test
    void createOccupation_whenDTOPeopleCountIsNull_shouldThrow() {
        CreateOccupationDTO input = new CreateOccupationDTO(
                LocalDateTime.now().minusMinutes(10),
                null,
                1L,
                null
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.createOccupation(input)
        );

        assertEquals("Quantidade de pessoas é inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    void createOccupation_whenDTOPeopleCountIsLessThanOrEqualToZero_shouldThrow(Integer peopleCount) {
        CreateOccupationDTO input = new CreateOccupationDTO(
                LocalDateTime.now().minusMinutes(10),
                peopleCount,
                1L,
                null
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.createOccupation(input)
        );

        assertEquals("Quantidade de pessoas é inválida", exception.getMessage());
    }

    @Test
    void createOccupation_whenDTOCustomerIdsIsEmpty_shouldSaveAndReturnOccupation() {
        Set<Customer> someCustomers = Set.of(
                new Customer(1L, "Cliente 01", null, null, true, null),
                new Customer(2L, "Cliente 02", null, null, true, null)
        );
        Occupation someOccupation = new Occupation(
                1L,
                LocalDateTime.now(),
                null,
                2,
                null,
                new ArrayList<>(),
                new Board(1L, 1, 2, BoardLocation.AREA_INTERNA, true),
                someCustomers,
                true
        );
        CreateOccupationDTO input = new CreateOccupationDTO(
                LocalDateTime.now().minusMinutes(10),
                2,
                1L,
                Set.of(1L, 2L)
        );
        Board board = new Board(1L, 1, 2, BoardLocation.AREA_INTERNA, true);

        when(this.boardRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(board);
        when(this.boardRepository.isBoardOccupied(anyLong())).thenReturn(false);
        when(this.occupationRepository.save(any(Occupation.class))).thenReturn(someOccupation);
        when(this.customerRepository.getCustomers(anySet())).thenReturn(someCustomers);

        ArgumentCaptor<Occupation> argumentCaptor = ArgumentCaptor.forClass(Occupation.class);

        OccupationDTO output = this.occupationService.createOccupation(input);

        verify(this.occupationRepository).save(argumentCaptor.capture());
        Occupation capturedOccupation = argumentCaptor.getValue();

        assertAll(
                () -> assertEquals(input.beginOccupation(), capturedOccupation.getBeginOccupation()),
                () -> assertEquals(input.peopleCount(), capturedOccupation.getPeopleCount()),
                () -> assertEquals(board, capturedOccupation.getBoard()),

                () -> assertEquals(someOccupation.getId(), output.id()),
                () -> assertEquals(someOccupation.getBeginOccupation(), output.beginOccupation()),
                () -> assertEquals(someOccupation.getEndOccupation(), output.endOccupation()),
                () -> assertEquals(someOccupation.getPeopleCount(), output.peopleCount()),
                () -> assertEquals(someOccupation.getPaymentForm(), output.paymentForm()),
                () -> assertEquals(someOccupation.getOrderItems().size(), output.orderItems().size()),
                () -> assertEquals(someOccupation.getBoard().getLocation(), output.board().location()),
                () -> assertEquals(someOccupation.getBoard().getNumber(), output.board().number()),
                () -> assertEquals(someOccupation.getCustomers().size(), output.customers().size()),
                () -> assertEquals(someCustomers.size(), output.customers().size())
        );
    }

    @Test
    void createOccupation_whenCustomersDoesNotExist_shouldThrow() {
        Occupation someOccupation = new Occupation(
                1L,
                LocalDateTime.now(),
                null,
                2,
                null,
                new ArrayList<>(),
                new Board(1L, 1, 2, BoardLocation.AREA_INTERNA, true),
                new HashSet<>(),
                true
        );
        CreateOccupationDTO input = new CreateOccupationDTO(
                LocalDateTime.now().minusMinutes(10),
                2,
                1L,
                Set.of(1L, 2L)
        );
        Board board = new Board(1L, 1, 2, BoardLocation.AREA_INTERNA, true);

        when(this.boardRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(board);
        when(this.boardRepository.isBoardOccupied(anyLong())).thenReturn(false);
        when(this.customerRepository.getCustomers(anySet())).thenReturn(new HashSet<>());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.createOccupation(input)
        );

        assertEquals("Clientes não existem ou foram desativados", exception.getMessage());
    }

    @Test
    void createOccupation_whenSomeCustomerDoesNotExist_shouldThrow() {
        Set<Customer> someCustomers = Set.of(
                new Customer(1L, "Cliente 01", null, null, true, null)
        );
        CreateOccupationDTO input = new CreateOccupationDTO(
                LocalDateTime.now().minusMinutes(10),
                2,
                1L,
                Set.of(1L, 2L)
        );
        Board board = new Board(1L, 1, 2, BoardLocation.AREA_INTERNA, true);

        when(this.boardRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(board);
        when(this.boardRepository.isBoardOccupied(anyLong())).thenReturn(false);
        when(this.customerRepository.getCustomers(anySet())).thenReturn(someCustomers);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.createOccupation(input)
        );

        assertEquals("Algum cliente não existe ou foi desativado", exception.getMessage());
    }

    @Test
    void addOrderItems_whenOccupationIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.addOrderItems(null, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void addOrderItems_whenOccupationIdIsLessThanOrEqualToZero_shouldThrow(Long occupationId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.addOrderItems(occupationId, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @Test
    void addOrderItems_whenDTOIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.addOrderItems(1L, null)
        );

        assertEquals("DTO inválido", exception.getMessage());
    }

    @Test
    void addOrderItems_whenDTOOrderItemsIsEmpty_shouldThrow() {
        AddOrderItemsDTO input = new AddOrderItemsDTO(
                new ArrayList<>()
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.addOrderItems(1L, input)
        );

        assertEquals("Lista de itens do pedido está vazia", exception.getMessage());
    }

    @Test
    void addOrderItems_whenDTOOrderItemsHasSomeProductIdBeenNull_shouldThrow() {
        AddOrderItemsDTO input = new AddOrderItemsDTO(
                Arrays.asList(
                        new CreateOrderItemDTO(1L, 2, null),
                        new CreateOrderItemDTO(null, 2, null)
                )
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.addOrderItems(1L, input)
        );

        assertEquals("Algum item possui a ID do produto é inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void addOrderItems_whenDTOOrderItemsHasSomeProductIdLessThanOrEqualToZero_shouldThrow(Long productId) {
        AddOrderItemsDTO input = new AddOrderItemsDTO(
                Arrays.asList(
                        new CreateOrderItemDTO(1L, 2, null),
                        new CreateOrderItemDTO(productId, 2, null)
                )
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.addOrderItems(1L, input)
        );

        assertEquals("Algum item possui a ID do produto é inválida", exception.getMessage());
    }

    @Test
    void addOrderItems_whenDTOOrderItemsHasSomeProductAmountBeenNull_shouldThrow() {
        AddOrderItemsDTO input = new AddOrderItemsDTO(
                Arrays.asList(
                        new CreateOrderItemDTO(1L, 2, null),
                        new CreateOrderItemDTO(2L, null, null)
                )
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.addOrderItems(1L, input)
        );

        assertEquals("Quantidade do produto é inválido", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    void addOrderItems_whenDTOOrderItemsHasSomeProductIdLessThanOrEqualToZero_shouldThrow(Integer productAmount) {
        AddOrderItemsDTO input = new AddOrderItemsDTO(
                Arrays.asList(
                        new CreateOrderItemDTO(1L, 2, null),
                        new CreateOrderItemDTO(2L, productAmount, null)
                )
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.addOrderItems(1L, input)
        );

        assertEquals("Quantidade do produto é inválido", exception.getMessage());
    }

    @Test
    void addOrderItems_whenOccupationDoesNotExist_shouldThrow() {
        AddOrderItemsDTO input = new AddOrderItemsDTO(
                Arrays.asList(
                        new CreateOrderItemDTO(1L, 2, null),
                        new CreateOrderItemDTO(2L, 1, null)
                )
        );

        when(this.occupationRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(null);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.addOrderItems(1L, input)
        );

        assertEquals("Ocupação não existe ou foi inativada", exception.getMessage());
    }

    @Test
    void addOrderItems_whenOccupationWasFinished_shouldThrow() {
        AddOrderItemsDTO input = new AddOrderItemsDTO(
                Arrays.asList(
                        new CreateOrderItemDTO(1L, 2, null),
                        new CreateOrderItemDTO(2L, 1, null)
                )
        );
        Occupation someOccupation = new Occupation(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                2,
                null,
                new ArrayList<>(),
                new Board(1L, 1, 2, BoardLocation.AREA_INTERNA, true),
                new HashSet<>(),
                true
        );

        when(this.occupationRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(someOccupation);

        IllegalCallerException exception = assertThrows(
                IllegalCallerException.class,
                () -> this.occupationService.addOrderItems(1L, input)
        );

        assertEquals("A ocupação já foi finalizada", exception.getMessage());
    }

    @Test
    void addOrderItems_whenDTOOrderItemsHasNonexistentProducts_shouldThrow() {
        AddOrderItemsDTO input = new AddOrderItemsDTO(
                Arrays.asList(
                        new CreateOrderItemDTO(1L, 2, null),
                        new CreateOrderItemDTO(2L, 1, null)
                )
        );
        Occupation someOccupation = new Occupation(
                1L,
                LocalDateTime.now(),
                null,
                2,
                null,
                new ArrayList<>(),
                new Board(1L, 1, 2, BoardLocation.AREA_INTERNA, true),
                new HashSet<>(),
                true
        );
        List<Product> someProducts = List.of(
                new Product(1L, "Hamburguer tradicional", 25.9, null, true)
        );

        when(this.occupationRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(someOccupation);
        when(this.productRepository.getReferenceByActiveTrueAndIdIn(anyList())).thenReturn(someProducts);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.addOrderItems(1L, input)
        );

        assertEquals("Existem produtos inválidos", exception.getMessage());
    }

    @Test
    void addOrderItems_whenDTOIsValidAndProductsExists_shouldSaveOrderItems() {
        AddOrderItemsDTO input = new AddOrderItemsDTO(
                Arrays.asList(
                        new CreateOrderItemDTO(1L, 2, null),
                        new CreateOrderItemDTO(2L, 1, null)
                )
        );
        Occupation someOccupation = new Occupation(
                1L,
                LocalDateTime.now(),
                null,
                2,
                null,
                new ArrayList<>(),
                new Board(1L, 1, 2, BoardLocation.AREA_INTERNA, true),
                new HashSet<>(),
                true
        );
        List<Product> someProducts = List.of(
                new Product(1L, "Hamburguer tradicional", 25.9, null, true),
                new Product(2L, "Hamburguer vegano", 21.5, null, true)
        );

        when(this.occupationRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(someOccupation);
        when(this.productRepository.getReferenceByActiveTrueAndIdIn(anyList())).thenReturn(someProducts);

        ArgumentCaptor<List<OrderItem>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        this.occupationService.addOrderItems(1L, input);
        verify(this.orderItemRepository, atMostOnce()).saveAll(anyList());

        verify(this.orderItemRepository).saveAll(argumentCaptor.capture());
        List<OrderItem> capturedValue = argumentCaptor.getValue();

        assertAll(
                () -> assertEquals(input.orderItems().get(0).amount(), capturedValue.get(0).getAmount()),
                () -> assertEquals(someProducts.get(0).getPrice(), capturedValue.get(0).getItemValue()),
                () -> assertEquals(someProducts.get(0), capturedValue.get(0).getProduct()),
                () -> assertEquals(someOccupation, capturedValue.get(0).getOccupation()),
                () -> assertEquals(input.orderItems().get(0).observation(), capturedValue.get(0).getObservation()),

                () -> assertEquals(input.orderItems().get(1).amount(), capturedValue.get(1).getAmount()),
                () -> assertEquals(someProducts.get(1).getPrice(), capturedValue.get(1).getItemValue()),
                () -> assertEquals(someProducts.get(1), capturedValue.get(1).getProduct()),
                () -> assertEquals(someOccupation, capturedValue.get(1).getOccupation()),
                () -> assertEquals(input.orderItems().get(1).observation(), capturedValue.get(1).getObservation())
        );
    }

    @Test
    void removeOrderItems_whenOccupationIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.removeOrderItems(null, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void removeOrderItems_whenOccupationIdIsLessThanOrEqualToZero_shouldThrow(Long occupationId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.removeOrderItems(occupationId, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @Test
    void removeOrderItems_whenDTOIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.removeOrderItems(1L, null)
        );

        assertEquals("DTO inválido", exception.getMessage());
    }

    @Test
    void removeOrderItems_whenDTOOrderItemsIsEmpty_shouldThrow() {
        RemoveOrderItemsDTO input = new RemoveOrderItemsDTO(List.of());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.removeOrderItems(1L, input)
        );

        assertEquals("Lista de itens do pedido está vazia", exception.getMessage());
    }

    @Test
    void removeOrderItems_whenOccupationDoesNotExist_shouldThrow() {
        RemoveOrderItemsDTO input = new RemoveOrderItemsDTO(List.of(1L, 2L));

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(false);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.removeOrderItems(1L, input)
        );

        assertEquals("Ocupação não existe ou foi inativada", exception.getMessage());
    }

    @Test
    void removeOrderItems_whenAllOrderItemsDoesNotBelongToOccupation_shouldThrow() {
        RemoveOrderItemsDTO input = new RemoveOrderItemsDTO(List.of(1L, 2L));

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.findOrderItems(anyLong(), anyList())).thenReturn(new ArrayList<>());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.removeOrderItems(1L, input)
        );

        assertEquals("Nenhum item pertence ao pedido", exception.getMessage());
    }

    @Test
    void removeOrderItems_whenOrderItemsDoesNotBelongToOccupation_shouldThrow() {
        RemoveOrderItemsDTO input = new RemoveOrderItemsDTO(List.of(1L, 2L));
        Occupation someOccupation = new Occupation();
        List<OrderItem> someOrderItems = List.of(
                new OrderItem(1L, 2, 20.3, OrderItemStatus.PRONTO, null, new Product(), someOccupation, true)
        );

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.findOrderItems(anyLong(), anyList())).thenReturn(someOrderItems);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.removeOrderItems(1L, input)
        );

        assertEquals("Existem itens que não pertencem ao pedido", exception.getMessage());
    }

    @Test
    void removeOrderItems_whenOrderItemsBelongsToOccupation_shouldInactivateAllOrderItems() {
        RemoveOrderItemsDTO input = new RemoveOrderItemsDTO(List.of(1L, 2L));
        Occupation someOccupation = new Occupation();
        List<OrderItem> someOrderItems = List.of(
                spy(new OrderItem(1L, 2, 20.3, OrderItemStatus.PRONTO, null, new Product(), someOccupation, true)),
                spy(new OrderItem(2L, 2, 20.3, OrderItemStatus.PRONTO, null, new Product(), someOccupation, true))
        );

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.findOrderItems(anyLong(), anyList())).thenReturn(someOrderItems);


        this.occupationService.removeOrderItems(1L, input);

        verify(someOrderItems.get(0), atMostOnce()).inactivate();
        verify(someOrderItems.get(1), atMostOnce()).inactivate();
    }

    @Test
    void updateOrderItem_whenOccupationIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.updateOrderItem(null, null, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void updateOrderItem_whenOccupationIdIsLessThanOrEqualToZero_shouldThrow(Long occupationId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.updateOrderItem(occupationId, null, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @Test
    void updateOrderItem_whenItemIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.updateOrderItem(1L, null, null)
        );

        assertEquals("ID do item inválido", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void updateOrderItem_whenItemIdIsLessThanOrEqualToZero_shouldThrow(Long itemId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.updateOrderItem(1L, itemId, null)
        );

        assertEquals("ID do item inválido", exception.getMessage());
    }

    @Test
    void updateOrderItem_whenDTOIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.updateOrderItem(1L, 1L, null)
        );

        assertEquals("DTO inválido", exception.getMessage());
    }

    @Test
    void updateOrderItem_whenDTOAmountIsNull_shouldThrow() {
        UpdateOrderItemDTO input = new UpdateOrderItemDTO(null, null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.updateOrderItem(1L, 1L, input)
        );

        assertEquals("Quantidade de itens inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    void updateOrderItem_whenDTOAmountIsLessThanOrEqualToZero_shouldThrow(Integer amount) {
        UpdateOrderItemDTO input = new UpdateOrderItemDTO(amount, null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.updateOrderItem(1L, 1L, input)
        );

        assertEquals("Quantidade de itens inválida", exception.getMessage());
    }

    @Test
    void updateOrderItem_whenOccupationDoesNotExist_shouldThrow() {
        UpdateOrderItemDTO input = new UpdateOrderItemDTO(2, null);

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(false);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.updateOrderItem(1L, 1L, input)
        );

        assertEquals("Ocupação não existe ou foi inativada", exception.getMessage());
    }

    @Test
    void updateOrderItem_whenItemDoesNotBelongToOccupation_shouldThrow() {
        UpdateOrderItemDTO input = new UpdateOrderItemDTO(2, null);

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(null);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.updateOrderItem(1L, 1L, input)
        );

        assertEquals("Item não existe ou não pertence a essa ocupação", exception.getMessage());
    }

    @Test
    void updateOrderItem_whenItemWasDelivered_shouldThrow() {
        UpdateOrderItemDTO input = new UpdateOrderItemDTO(2, null);
        OrderItem someOrderItem = new OrderItem(1L, 2, 23.5, OrderItemStatus.ENTREGUE, null, new Product(), new Occupation(), true);

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(someOrderItem);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> this.occupationService.updateOrderItem(1L, 1L, input)
        );

        assertEquals("O item já foi entregue e, portanto, não pode ser mais alterado", exception.getMessage());
    }

    @Test
    void updateOrderItem_whenItemIsValid_shouldUpdateOrderItem() {
        UpdateOrderItemDTO input = new UpdateOrderItemDTO(2, null);
        OrderItem someOrderItemSpy = spy(new OrderItem(1L, 2, 23.5, OrderItemStatus.EM_ANDAMENTO, null, new Product(), new Occupation(), true));

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(someOrderItemSpy);

        this.occupationService.updateOrderItem(1L, 1L, input);

        verify(someOrderItemSpy, atMostOnce()).update(any(UpdateOrderItemDTO.class));
    }

    @Test
    void inactivateOccupation_whenOccupationIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.inactivateOccupation(null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void inactivateOccupation_whenOccupationIdIsLessThanOrEqualToZero_shouldThrow(Long occupationId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.inactivateOccupation(occupationId)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @Test
    void inactivateOccupation_whenOccupationDoesNotExist_shouldThrow() {
        when(this.occupationRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(null);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.inactivateOccupation(1L)
        );

        assertEquals("Ocupação não existe", exception.getMessage());
    }

    @Test
    void inactivateOccupation_whenOccupationIsValid_shouldInactivateOccupationAndItsOrderItems() {
        List<OrderItem> someOrderItems = Arrays.asList(
                spy(new OrderItem(2, 23.5, new Product())),
                spy(new OrderItem(1, 23.5, new Product()))
        );
        Occupation someOccupationSpy = spy(new Occupation(
                1L,
                LocalDateTime.now(),
                null,
                2,
                PaymentForm.CARTAO_CREDITO,
                someOrderItems,
                new Board(),
                new HashSet<>(),
                true
        ));


        when(this.occupationRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(someOccupationSpy);
        when(this.orderItemRepository.findByOccupationId(anyLong())).thenReturn(someOrderItems);

        this.occupationService.inactivateOccupation(1L);

        verify(someOccupationSpy, atMostOnce()).inactivate();
        verify(someOrderItems.get(0), atMostOnce()).inactivate();
        verify(someOrderItems.get(1), atMostOnce()).inactivate();

        assertFalse(someOccupationSpy.isActive());
        assertFalse(someOrderItems.get(0).isActive());
        assertFalse(someOrderItems.get(1).isActive());
    }

    @Test
    void startOrderItemPreparation_whenOccupationIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.startOrderItemPreparation(null, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void startOrderItemPreparation_whenOccupationIdIsLessThanOrEqualToZero_shouldThrow(Long occupationId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.startOrderItemPreparation(occupationId, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @Test
    void startOrderItemPreparation_whenItemIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.startOrderItemPreparation(1L, null)
        );

        assertEquals("ID do item inválido", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void startOrderItemPreparation_whenItemIdIsLessThanOrEqualToZero_shouldThrow(Long itemId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.startOrderItemPreparation(1L, itemId)
        );

        assertEquals("ID do item inválido", exception.getMessage());
    }

    @Test
    void startOrderItemPreparation_whenOccupationDoesNotExists_shouldThrow() {
        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(false);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.startOrderItemPreparation(1L, 1L)
        );

        assertEquals("Ocupação não existe ou foi inativada", exception.getMessage());
    }

    @Test
    void startOrderItemPreparation_whenItemDoesNotExists_shouldThrow() {
        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(null);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.startOrderItemPreparation(1L, 1L)
        );

        assertEquals("Item não existe ou não pertence a essa ocupação", exception.getMessage());
    }

    @Test
    void startOrderItemPreparation_whenItemIsInProgress_shouldThrow() {
        OrderItem someOrderItem = new OrderItem(
                2,
                21.9,
                new Product(),
                new Occupation(),
                null
        );
        someOrderItem.startPreparation();

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(someOrderItem);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> this.occupationService.startOrderItemPreparation(1L, 1L)
        );

        assertEquals("O item já está sendo preparado", exception.getMessage());
    }

    @Test
    void startOrderItemPreparation_whenWasReceived_shouldChangeToInProgressState() {
        OrderItem someOrderItemSpy = spy(new OrderItem(
                2,
                21.9,
                new Product(),
                new Occupation(),
                null
        ));

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(someOrderItemSpy);


        this.occupationService.startOrderItemPreparation(1L, 1L);

        verify(someOrderItemSpy, atMostOnce()).startPreparation();
        assertEquals(OrderItemStatus.EM_ANDAMENTO, someOrderItemSpy.getStatus());
    }

    @Test
    void finishOrderItemPreparation_whenOccupationIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.finishOrderItemPreparation(null, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void finishOrderItemPreparation_whenOccupationIdIsLessThanOrEqualToZero_shouldThrow(Long occupationId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.finishOrderItemPreparation(occupationId, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @Test
    void finishOrderItemPreparation_whenItemIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.finishOrderItemPreparation(1L, null)
        );

        assertEquals("ID do item inválido", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void finishOrderItemPreparation_whenItemIdIsLessThanOrEqualToZero_shouldThrow(Long itemId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.finishOrderItemPreparation(1L, itemId)
        );

        assertEquals("ID do item inválido", exception.getMessage());
    }

    @Test
    void finishOrderItemPreparation_whenOccupationDoesNotExists_shouldThrow() {
        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(false);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.finishOrderItemPreparation(1L, 1L)
        );

        assertEquals("Ocupação não existe ou foi inativada", exception.getMessage());
    }

    @Test
    void finishOrderItemPreparation_whenItemDoesNotExists_shouldThrow() {
        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(null);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.finishOrderItemPreparation(1L, 1L)
        );

        assertEquals("Item não existe ou não pertence a essa ocupação", exception.getMessage());
    }

    @Test
    void finishOrderItemPreparation_whenItemIsInProgress_shouldThrow() {
        OrderItem someOrderItem = new OrderItem(
                2,
                21.9,
                new Product(),
                new Occupation(),
                null
        );
        someOrderItem.finishPreparation();

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(someOrderItem);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> this.occupationService.finishOrderItemPreparation(1L, 1L)
        );

        assertEquals("O preparo do item já foi finalizado", exception.getMessage());
    }

    @Test
    void finishOrderItemPreparation_whenWasReceived_shouldChangeToInProgressState() {
        OrderItem someOrderItemSpy = spy(new OrderItem(
                2,
                21.9,
                new Product(),
                new Occupation(),
                null
        ));

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(someOrderItemSpy);


        this.occupationService.finishOrderItemPreparation(1L, 1L);

        verify(someOrderItemSpy, atMostOnce()).finishPreparation();
        assertEquals(OrderItemStatus.PRONTO, someOrderItemSpy.getStatus());
    }

    @Test
    void deliverOrderItem_whenOccupationIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.deliverOrderItem(null, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void deliverOrderItem_whenOccupationIdIsLessThanOrEqualToZero_shouldThrow(Long occupationId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.deliverOrderItem(occupationId, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @Test
    void deliverOrderItem_whenItemIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.deliverOrderItem(1L, null)
        );

        assertEquals("ID do item inválido", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void deliverOrderItem_whenItemIdIsLessThanOrEqualToZero_shouldThrow(Long itemId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.deliverOrderItem(1L, itemId)
        );

        assertEquals("ID do item inválido", exception.getMessage());
    }

    @Test
    void deliverOrderItem_whenOccupationDoesNotExists_shouldThrow() {
        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(false);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.deliverOrderItem(1L, 1L)
        );

        assertEquals("Ocupação não existe ou foi inativada", exception.getMessage());
    }

    @Test
    void deliverOrderItem_whenItemDoesNotExists_shouldThrow() {
        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(null);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.deliverOrderItem(1L, 1L)
        );

        assertEquals("Item não existe ou não pertence a essa ocupação", exception.getMessage());
    }

    @Test
    void deliverOrderItem_whenItemIsInProgress_shouldThrow() {
        OrderItem someOrderItem = new OrderItem(
                2,
                21.9,
                new Product(),
                new Occupation(),
                null
        );
        someOrderItem.deliver();

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(someOrderItem);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> this.occupationService.deliverOrderItem(1L, 1L)
        );

        assertEquals("O item já foi entregue ao cliente", exception.getMessage());
    }

    @Test
    void deliverOrderItem_whenWasReceived_shouldChangeToInProgressState() {
        OrderItem someOrderItemSpy = spy(new OrderItem(
                2,
                21.9,
                new Product(),
                new Occupation(),
                null
        ));

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(someOrderItemSpy);


        this.occupationService.deliverOrderItem(1L, 1L);

        verify(someOrderItemSpy, atMostOnce()).deliver();
        assertEquals(OrderItemStatus.ENTREGUE, someOrderItemSpy.getStatus());
    }

    @Test
    void cancelOrderItem_whenOccupationIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.cancelOrderItem(null, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void cancelOrderItem_whenOccupationIdIsLessThanOrEqualToZero_shouldThrow(Long occupationId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.cancelOrderItem(occupationId, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @Test
    void cancelOrderItem_whenItemIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.cancelOrderItem(1L, null)
        );

        assertEquals("ID do item inválido", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void cancelOrderItem_whenItemIdIsLessThanOrEqualToZero_shouldThrow(Long itemId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.cancelOrderItem(1L, itemId)
        );

        assertEquals("ID do item inválido", exception.getMessage());
    }

    @Test
    void cancelOrderItem_whenOccupationDoesNotExists_shouldThrow() {
        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(false);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.cancelOrderItem(1L, 1L)
        );

        assertEquals("Ocupação não existe ou foi inativada", exception.getMessage());
    }

    @Test
    void cancelOrderItem_whenItemDoesNotExists_shouldThrow() {
        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(null);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.cancelOrderItem(1L, 1L)
        );

        assertEquals("Item não existe ou não pertence a essa ocupação", exception.getMessage());
    }

    @Test
    void cancelOrderItem_whenItemIsInProgress_shouldThrow() {
        OrderItem someOrderItem = new OrderItem(
                2,
                21.9,
                new Product(),
                new Occupation(),
                null
        );
        someOrderItem.cancel();

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(someOrderItem);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> this.occupationService.cancelOrderItem(1L, 1L)
        );

        assertEquals("O item já foi cancelado", exception.getMessage());
    }

    @Test
    void cancelOrderItem_whenWasReceived_shouldChangeToInProgressState() {
        OrderItem someOrderItemSpy = spy(new OrderItem(
                2,
                21.9,
                new Product(),
                new Occupation(),
                null
        ));

        when(this.occupationRepository.existsByIdAndActiveTrue(anyLong())).thenReturn(true);
        when(this.orderItemRepository.getReferenceByIdAndOccupationIdAndActiveTrue(anyLong(), anyLong())).thenReturn(someOrderItemSpy);

        this.occupationService.cancelOrderItem(1L, 1L);

        verify(someOrderItemSpy, atMostOnce()).cancel();
        assertEquals(OrderItemStatus.CANCELADO, someOrderItemSpy.getStatus());
    }

    @Test
    void finishOccupation_whenOccupationIdIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.finishOccupation(null, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -10L})
    void finishOccupation_whenOccupationIdIsLessThanOrEqualToZero_shouldThrow(Long occupationId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.finishOccupation(occupationId, null)
        );

        assertEquals("ID da ocupação inválida", exception.getMessage());
    }

    @Test
    void finishOccupation_whenDTOIsNull_shouldThrow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.finishOccupation(1L, null)
        );

        assertEquals("DTO inválido", exception.getMessage());
    }

    @Test
    void finishOccupation_whenDTOEndOccupationIsNull_shouldThrow() {
        FinishOccupationDTO input = new FinishOccupationDTO(null, null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.finishOccupation(1L, input)
        );

        assertEquals("Término da ocupação inválido", exception.getMessage());
    }

    @Test
    void finishOccupation_whenDTOEndOccupationIsBeforeNow_shouldThrow() {
        FinishOccupationDTO input = new FinishOccupationDTO(
                LocalDateTime.now().minusMinutes(20),
                null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.finishOccupation(1L, input)
        );

        assertEquals("Término da ocupação deve ser igual ou maior que a data atual", exception.getMessage());
    }

    @Test
    void finishOccupation_whenDTOPaymentFormIsNull_shouldThrow() {
        FinishOccupationDTO input = new FinishOccupationDTO(
                LocalDateTime.now().plusMinutes(1),
                null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.occupationService.finishOccupation(1L, input)
        );

        assertEquals("Forma de pagamento inválida", exception.getMessage());
    }

    @Test
    void finishOccupation_whenOccupationDoesNotExist_shouldThrow() {
        FinishOccupationDTO input = new FinishOccupationDTO(
                LocalDateTime.now().plusMinutes(1),
                PaymentForm.CARTAO_CREDITO);

        when(this.occupationRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(null);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> this.occupationService.finishOccupation(1L, input)
        );

        assertEquals("Ocupação não existe ou foi inativada", exception.getMessage());
    }

    @Test
    void finishOccupation_whenOccupationIsFinished_shouldThrow() {
        FinishOccupationDTO input = new FinishOccupationDTO(
                LocalDateTime.now().plusMinutes(1),
                PaymentForm.CARTAO_CREDITO);

        Occupation someOccupation = new Occupation(
                1L,
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now().plusMinutes(1),
                2,
                PaymentForm.CARTAO_CREDITO,
                List.of(new OrderItem()),
                new Board(),
                new HashSet<>(),
                true
        );

        when(this.occupationRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(someOccupation);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> this.occupationService.finishOccupation(1L, input)
        );

        assertEquals("A ocupação já foi finalizada", exception.getMessage());
    }

    @Test
    void finishOccupation_whenOccupationHasPendingOrderItens_shouldThrow() {
        FinishOccupationDTO input = new FinishOccupationDTO(
                LocalDateTime.now().plusMinutes(1),
                PaymentForm.CARTAO_CREDITO);

        OrderItem someOrderItem = new OrderItem(1, 23.5, new Product());
        someOrderItem.startPreparation();

        Occupation someOccupation = new Occupation(
                1L,
                LocalDateTime.now().minusMinutes(10),
                null,
                2,
                PaymentForm.CARTAO_CREDITO,
                List.of(someOrderItem),
                new Board(),
                new HashSet<>(),
                true
        );

        when(this.occupationRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(someOccupation);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> this.occupationService.finishOccupation(1L, input)
        );

        assertEquals("Não é possível finalizar a ocupação tendo itens pendentes", exception.getMessage());
    }

    @Test
    void finishOccupation_whenOccupationCanBeFinish_shouldShouldFinishOccupation() {
        FinishOccupationDTO input = new FinishOccupationDTO(
                LocalDateTime.now().plusMinutes(1),
                PaymentForm.CARTAO_CREDITO);

        OrderItem someOrderItem = new OrderItem(1, 23.5, new Product());
        someOrderItem.deliver();

        Occupation someOccupationSpy = spy(new Occupation(
                1L,
                LocalDateTime.now().minusMinutes(10),
                null,
                2,
                PaymentForm.CARTAO_CREDITO,
                List.of(someOrderItem),
                new Board(),
                new HashSet<>(),
                true
        ));

        when(this.occupationRepository.getReferenceByIdAndActiveTrue(anyLong())).thenReturn(someOccupationSpy);

        this.occupationService.finishOccupation(1L, input);

        verify(someOccupationSpy, atMostOnce()).finish(any(FinishOccupationDTO.class));
        assertEquals(input.endOccupation(), someOccupationSpy.getEndOccupation());
        assertEquals(input.paymentForm(), someOccupationSpy.getPaymentForm());

    }
}
