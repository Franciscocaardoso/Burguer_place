package br.com.senior.delivery.controller;

import br.com.senior.delivery.domain.customer.Customer;
import br.com.senior.delivery.domain.customer.dto.CustomerRegistrationData;
import br.com.senior.delivery.domain.customer.CustomerService;
import br.com.senior.delivery.domain.customer.dto.CustomerUploadData;
import br.com.senior.delivery.domain.customer.dto.listingDataCustomers;
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
    public ResponseEntity register(@RequestBody @Valid CustomerRegistrationData data){
        Customer customer = customerService.addCustomer(data);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @GetMapping
    public Page<listingDataCustomers> listCustomer(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        return customerService.listCustomer(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity listCustomerById(@PathVariable Long id){
        Customer customer = customerService.listCustomerById(id);
        return ResponseEntity.ok(new listingDataCustomers(customer));
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateCustomer(@RequestBody @Valid CustomerUploadData data){
        customerService.updateCustomer(data);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
