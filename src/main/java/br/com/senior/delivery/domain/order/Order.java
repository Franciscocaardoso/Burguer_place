package br.com.senior.delivery.domain.order;

import br.com.senior.delivery.domain.customer.Customer;
import br.com.senior.delivery.domain.order_item.OrderItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "order")
@Entity(name = "Order")
public class Order {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String status;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private PaymentForm paymentForm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderItem> orderItems;
}
