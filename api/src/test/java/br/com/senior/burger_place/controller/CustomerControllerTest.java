package br.com.senior.burger_place.controller;

import br.com.senior.burger_place.domain.address.AdressDto;
import br.com.senior.burger_place.domain.customer.CustomerService;
import br.com.senior.burger_place.domain.customer.dto.CustomerRegistrationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private CustomerService customerService;

    @Autowired
    private JacksonTester<CustomerRegistrationDTO> jsonDto;
    @Autowired
    private JacksonTester<AdressDto> jsonAdressDto;

    @Test
    public void register_whenJsonFileIsNotNull_shouldReturnHttpStatus200() throws Exception {
        AdressDto adressDto = new AdressDto("Avenida Principal", "Bairro II", "Cidade II", "Estado II", "89120000", "22", "Apartamento 3");
        CustomerRegistrationDTO dto = new CustomerRegistrationDTO("Ana Silva", "ana.silva123@email.com", "98765432110", adressDto);


        MockHttpServletResponse response = mvc.perform(
                post("/customers")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void register_whenJsonFileIsNull_shouldReturnHttpStatus400() throws Exception {

        String json = "{}";

        MockHttpServletResponse response = mvc.perform(
                post("/customers")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }
}