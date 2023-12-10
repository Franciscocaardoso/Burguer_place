package br.com.senior.e_commerce.controller;

import br.com.senior.e_commerce.domain.client.CustomerRegistrationData;
import br.com.senior.e_commerce.domain.client.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    public void register(@RequestBody @Valid CustomerRegistrationData data){
        clientService.addCustomer(data);
    }
}
