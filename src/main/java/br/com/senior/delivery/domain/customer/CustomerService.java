package br.com.senior.delivery.domain.customer;

import br.com.senior.delivery.domain.customer.dto.CustomerRegistrationData;
import br.com.senior.delivery.domain.customer.dto.CustomerUploadData;
import br.com.senior.delivery.domain.customer.dto.listingDataCustomers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository clientRepository;

    public Customer addCustomer(CustomerRegistrationData data){
        return clientRepository.save(new Customer(data));

    }

    public Page<listingDataCustomers> listCustomer(Pageable pageable) {
        return clientRepository.findAll(pageable).map(listingDataCustomers::new);
    }
// COMENTEI PARA TESTAR SE DE FATO É DESNECESSÁRIO. A PRINCIPIO CONSIGO TRATAR TUDO DIRETANENTE NO HANDLER
    public Customer listCustomerById(Long id) {
        return clientRepository.findById(id).get();
//                .orElseThrow(() -> new NoSuchElementException("Cliente não cadastrado"));
    }

    public void deleteCustomer(Long id) {
//        if (clientRepository.findById(id).isEmpty()){
//            throw new NoSuchElementException();
//        }
        clientRepository.deleteById(id);
    }

    public void updateCustomer(CustomerUploadData data) {
        Customer customer = clientRepository.getReferenceById(data.id());
        customer.updateInformation(data);
    }
}
