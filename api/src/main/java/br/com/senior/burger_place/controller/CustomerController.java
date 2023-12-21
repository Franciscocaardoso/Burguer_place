package br.com.senior.burger_place.controller;

import br.com.senior.burger_place.domain.customer.Customer;
import br.com.senior.burger_place.domain.customer.dto.CustomerRegistrationDTO;
import br.com.senior.burger_place.domain.customer.CustomerService;
import br.com.senior.burger_place.domain.customer.dto.CustomerUploadDTO;
import br.com.senior.burger_place.domain.customer.dto.listingCustomersDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> register(@RequestBody @Valid CustomerRegistrationDTO dto) {
        Customer customer = customerService.addCustomer(dto);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @GetMapping
    public Page<listingCustomersDTO> listCustomer(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        return customerService.listCustomer(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listCustomerById(@PathVariable Long id) {
        Customer customer = customerService.listCustomerById(id);
        return ResponseEntity.ok(new listingCustomersDTO(customer));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> updateCustomer(
            @PathVariable
            Long id,
            @RequestBody
            @Valid
            CustomerUploadDTO dto
    ) {
        customerService.updateCustomer(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.listCustomerById(id);
        customer.inactivate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
