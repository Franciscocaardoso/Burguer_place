package br.com.senior.burger_place.domain.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer getReferenceByIdAndActiveTrue(Long aLong);
}
