package br.com.senior.delivery.domain.order;

import br.com.senior.delivery.domain.customer.Customer;
import br.com.senior.delivery.domain.customer.CustomerRepository;
import br.com.senior.delivery.domain.order.dto.CreateOrderData;
import br.com.senior.delivery.domain.order.dto.OrderData;
import br.com.senior.delivery.domain.order.dto.OrderItemData;
import br.com.senior.delivery.domain.product.Product;
import br.com.senior.delivery.domain.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    public Page<OrderData> getOrders(Pageable pageable) {
        return this.orderRepository
                .findAll(pageable)
                .map(OrderData::new);
    }

    public Optional<OrderData> showOrder(Long id) {
        Order order = this.orderRepository.getReferenceByIdAndActiveTrue(id);

        if (order == null) {
            return Optional.empty();
        }

        return Optional.of(new OrderData(order));
    }

    public OrderData createOrder(CreateOrderData orderData) {
        Customer customer = this.customerRepository.getReferenceByIdAndActiveTrue(orderData.clientId());
        if (customer == null) {
            throw new EntityNotFoundException("Cliente não existe ou está inativado");
        }

        List<Product> products = this.productRepository.getProductPriceById(
                orderData.orderItems()
                        .stream()
                        .map(OrderItemData::productId)
                        .toList()
        );

        if (products.size() != orderData.orderItems().size()) {
            throw new EntityNotFoundException("Existem produtos inválidos!");
        }

        List<OrderItem> orderItems = orderData.orderItems().stream().map(item -> {
            Product prod = products.stream()
                    .filter(p -> Objects.equals(p.getId(), item.productId()))
                    .findFirst()
                    .get();

            return new OrderItem(item.amount(), prod.getPrice(), prod);
        }).toList();

        Order order = new Order(
                LocalDateTime.now(),
                null,
                orderData.paymentForm(),
                OrderStatus.RECEBIDO,
                customer,
                orderItems
        );

        this.orderRepository.save(order);
        return new OrderData(order);
    }
}
