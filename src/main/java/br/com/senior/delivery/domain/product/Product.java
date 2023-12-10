package br.com.senior.delivery.domain.product;

import br.com.senior.delivery.domain.customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "product")
@Entity(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String description;

}
