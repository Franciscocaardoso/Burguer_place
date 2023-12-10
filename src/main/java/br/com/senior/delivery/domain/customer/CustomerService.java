package br.com.senior.delivery.domain.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository clientRepository;

    public Customer addCustomer(CustomerRegistrationData data){
        return clientRepository.save(new Customer(data));

    }
}
