package br.com.senior.delivery.domain.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer getReferenceByIdAndActiveTrue(Long id);

    Page<Customer> findAllByActiveTrue(Pageable pageable);

    boolean existsByCpf(String name);

    boolean existsByEmail(String email);
}
