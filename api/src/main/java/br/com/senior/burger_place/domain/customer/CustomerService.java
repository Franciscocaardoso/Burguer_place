package br.com.senior.burger_place.domain.customer;

import br.com.senior.burger_place.domain.customer.dto.CustomerRegistrationDTO;
import br.com.senior.burger_place.domain.customer.dto.CustomerUploadDTO;
import br.com.senior.burger_place.domain.customer.dto.listingCustomersDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Customer addCustomer(CustomerRegistrationDTO data) {
        if (customerRepository.existsByCpf(data.cpf())) {
            throw new DuplicateKeyException("Já existe um cliente com o mesmo cpf.");
        }
        if (customerRepository.existsByEmail(data.email())) {
            throw new DuplicateKeyException("Já existe um cliente com o mesmo e-mail.");
        }
        return customerRepository.save(new Customer(data));
    }

    public Page<listingCustomersDTO> listCustomer(@PageableDefault(size = 5) Pageable pageable) {
        return customerRepository.findAllByActiveTrue(pageable).map(listingCustomersDTO::new);
    }

    public Customer listCustomerById(Long id) {
        Customer customer =  customerRepository.getReferenceByIdAndActiveTrue(id);
        if (customer == null){
            throw new EntityNotFoundException("Cliente não existe ou está inativado");
        }
        return customer;
    }

    public void updateCustomer(Long id, CustomerUploadDTO data) {
        Customer customer = customerRepository.getReferenceByIdAndActiveTrue(id);
        if (customer == null){
            throw new EntityNotFoundException("Cliente não existe ou está inativado");
        }
        customer.updateInformation(data);
    }
}