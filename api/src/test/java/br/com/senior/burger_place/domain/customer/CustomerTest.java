package br.com.senior.burger_place.domain.customer;

import br.com.senior.burger_place.domain.address.Address;
import br.com.senior.burger_place.domain.address.AdressDto;
import br.com.senior.burger_place.domain.customer.dto.CustomerRegistrationDTO;
import br.com.senior.burger_place.domain.customer.dto.CustomerUploadDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerTest {

    @Mock
    private Address address;
    @InjectMocks
    private Customer customer;
    @Test
    public void updateInformation_whenNoNullValues_shouldUpdateData(){

        AdressDto adressDto = new AdressDto("Rua A", "Bairro A", "Cidade A", "Estado A", "88888888", null, null);
        CustomerUploadDTO customerUploadDTO = new CustomerUploadDTO("Felipe", "felipe@email.com", adressDto);

        customer.updateInformation(customerUploadDTO);

        assertEquals("Felipe", customer.getName());
        assertEquals("felipe@email.com", customer.getEmail());

        verify(address, times(1)).updateInformation(adressDto);
    }

    @Test
    public void inactivate_whenInactivateIsCalled_activeAttributeShouldBeFalse(){

        Customer customer = new Customer(new CustomerRegistrationDTO("Ricardo Almeira", "Ricardo@email.com", "99999999900", mock(AdressDto.class), true));

        assertTrue(customer.isActive());
        customer.inactivate();
        assertFalse(customer.isActive());
    }
}