package br.com.senior.delivery.domain.order;

import br.com.senior.delivery.domain.order.Order;
import br.com.senior.delivery.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "order_items")
@Entity(name = "OrderItem")
@EqualsAndHashCode(of = "id")
public class OrderItem {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private int qtdItens;
    private double itemValue;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    private boolean active;

    public OrderItem(int qtdItens, double itemValue, Product product) {
        this.qtdItens = qtdItens;
        this.itemValue = itemValue;
        this.product = product;
        this.active = true;
    }

    public OrderItem(int qtdItens, double itemValue, Product product, Order order) {
        this(qtdItens, itemValue, product);
        this.order = order;
    }
}
