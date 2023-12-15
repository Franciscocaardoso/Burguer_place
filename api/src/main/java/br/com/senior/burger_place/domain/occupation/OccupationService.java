package br.com.senior.burger_place.domain.occupation;

import br.com.senior.burger_place.domain.board.Board;
import br.com.senior.burger_place.domain.board.BoardRepository;
import br.com.senior.burger_place.domain.occupation.dto.CreateOccupationData;
import br.com.senior.burger_place.domain.occupation.dto.ListOccupationDTO;
import br.com.senior.burger_place.domain.occupation.dto.OccupationData;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OccupationService {
    @Autowired
    private OccupationRepository occupationRepository;
    @Autowired
    private BoardRepository boardRepository;
    //    @Autowired
//    private OrderItemRepository orderItemRepository;
//    @Autowired
//    private CustomerRepository customerRepository;
//    @Autowired
//    private ProductRepository productRepository;
//
    public Page<ListOccupationDTO> listOccupations(Pageable pageable) {
        return this.occupationRepository.findAllByActiveTrue(pageable).map(ListOccupationDTO::new);
    }

    public Optional<OccupationData> showOccupation(Long id) {
        Occupation occupation = this.occupationRepository.getReferenceByIdAndActiveTrue(id);

        if (occupation == null) {
            return Optional.empty();
        }

        return Optional.of(new OccupationData(occupation));
    }

    public OccupationData createOccupation(CreateOccupationData orderData) {
        Board board = this.boardRepository.getReferenceByIdAndActiveTrue(orderData.boardId());

        if (board == null) {
            throw new EntityNotFoundException("Mesa não existe ou foi inativada");
        }

        if (this.boardRepository.isBoardOccupied(orderData.boardId())) {
            throw new IllegalArgumentException("Mesa já está ocupada");
        }

        if (orderData.beginOccupation().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de entrada deve ser menor ou igual a atual");
        }

        new Occupation();

        return null;
    }
//
//    public void addOrderItemToOrder(Long orderId, AddNewOrderItemsData orderItemsData) {
//        if (!this.occupationRepository.existsByIdAndActiveTrue(orderId)) {
//            throw new EntityNotFoundException("Pedido não existe");
//        }
//
//        if (orderItemsData.orderItems().isEmpty()) {
//            throw new IllegalArgumentException("Lista de itens do pedido está vazia");
//        }
//
//        List<Product> products = this.productRepository.getProductPriceById(
//                orderItemsData.orderItems()
//                        .stream()
//                        .map(CreateOrderItemData::productId)
//                        .toList()
//        );
//
//        if (products.size() != orderItemsData.orderItems().size()) {
//            throw new EntityNotFoundException("Existem produtos inválidos!");
//        }
//
//        List<OrderItem> orderItems = orderItemsData.orderItems().stream().map(item -> {
//            Product prod = products.stream()
//                    .filter(p -> Objects.equals(p.getId(), item.productId()))
//                    .findFirst()
//                    .get();
//
//            return new OrderItem(item.amount(), prod.getPrice(), prod);
//        }).toList();
//
//        this.orderItemRepository.saveAll(orderItems);
//    }
//
//    public void removeOrderItems(Long orderId, RemoveOrderItemsData orderItemsData) {
//        if (!this.occupationRepository.existsByIdAndActiveTrue(orderId)) {
//            throw new EntityNotFoundException("Pedido não existe");
//        }
//
//        if (orderItemsData.orderItems().isEmpty()) {
//            throw new IllegalArgumentException("Lista de itens do pedido está vazia");
//        }
//
//        List<OrderItem> orderItems = this.orderItemRepository.findOrderItems(orderId, orderItemsData.orderItems());
//
//        if (orderItems.isEmpty()) {
//            throw new EntityNotFoundException("Nenhum item pertence ao pedido");
//        }
//
//        if (orderItemsData.orderItems().size() > orderItems.size()) {
//            throw new IllegalArgumentException("Existem itens que não pertencem ao pedido");
//        }
//
//        orderItemsData.orderItems()
//                .forEach(orderItemId -> {
//                    OrderItem orderItem = orderItems.stream()
//                            .filter(item -> item.getId().equals(orderItemId))
//                            .findFirst()
//                            .get();
//
//                    orderItem.inactivate();
//                });
//    }
//
//    public void removeOrderItemFromOrder(Long orderId, Long orderItemId) {
//        if (!this.occupationRepository.existsByIdAndActiveTrue(orderId)) {
//            throw new EntityNotFoundException("Pedido não existe");
//        }
//
//        OrderItem orderItem = this.orderItemRepository.getReferenceByIdAndOrderId(orderItemId, orderId);
//
//        if (orderItem == null) {
//            throw new EntityNotFoundException("Item não existe ou não pertence a esse pedido");
//        }
//
//        orderItem.inactivate();
//    }
//
//    public void updateOrderItem(Long orderId, Long orderItemId, UpdateOrderItemData orderItemData) {
//        if (!this.occupationRepository.existsByIdAndActiveTrue(orderId)) {
//            throw new EntityNotFoundException("Pedido não existe");
//        }
//
//        OrderItem orderItem = this.orderItemRepository.getReferenceByIdAndOrderId(orderItemId, orderId);
//
//        if (orderItem == null) {
//            throw new EntityNotFoundException("Item não existe ou não pertence a esse pedido");
//        }
//
//        orderItem.update(orderItemData);
//    }
//
//    public void inactivateOrder(Long orderId) {
//        Occupation occupation = this.occupationRepository.getReferenceByIdAndActiveTrue(orderId);
//
//        if (occupation == null) {
//            throw new EntityNotFoundException("Pedido não existe");
//        }
//        occupation.inactivate();
//
//        List<OrderItem> orderItems = this.orderItemRepository.findByOrderIdAndActiveTrue(orderId);
//
//        if (!orderItems.isEmpty()) {
//            orderItems.forEach(OrderItem::inactivate);
//        }
//    }
}
