package br.com.senior.e_commerce.domain.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Customer addCustomer(CustomerRegistrationData data){
        return clientRepository.save(new Customer(data));

    }
}
