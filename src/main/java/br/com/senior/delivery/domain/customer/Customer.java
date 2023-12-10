package br.com.senior.delivery.domain.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "customer")
@Entity(name = "Customer")
public class Customer {
        @Id
        @GeneratedValue(strategy =  GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String email;
        private String cpf;
        private String postalCode;
        private int residentialNumber;
        private String complement;

        public Customer(CustomerRegistrationData data) {
                this.name = data.name();
                this.email = data.email();
                this.cpf = data.cpf();
                this.postalCode = data.postalCode();
                this.complement = data.complement();
        }
}
