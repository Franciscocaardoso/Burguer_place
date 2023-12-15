package br.com.senior.burger_place.domain.occupation;

import br.com.senior.burger_place.domain.occupation.dto.UpdateOrderItemData;
import br.com.senior.burger_place.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Table(name = "order_items")
@Entity(name = "OrderItem")
@SQLRestriction("active = TRUE")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;
    private double itemValue;
    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;
    private String observation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_id")
    private Occupation occupation;
    private boolean active;

    public OrderItem(int amount, double itemValue, Product product) {
        this.amount = amount;
        this.itemValue = itemValue;
        this.product = product;
        this.active = true;
    }

    public OrderItem(int amount, double itemValue, Product product, Occupation occupation) {
        this(amount, itemValue, product);
        this.occupation = occupation;
    }

    public void inactivate() {
        this.active = false;
    }

    public void update(UpdateOrderItemData orderItemData) {
        if (orderItemData.amount() != null && orderItemData.amount() > 0) {
            this.amount = orderItemData.amount();
        }
    }
}
