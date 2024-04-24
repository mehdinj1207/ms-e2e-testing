package net.njema.customerservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.njema.customerservice.dtos.CustomerDto;
import net.njema.customerservice.exceptions.CustomerNotFoundException;
import net.njema.customerservice.services.CustomerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
@ActiveProfiles("test")
@WebMvcTest(CustomerRestController.class)
class CustomerRestControllerTest {
    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    List<CustomerDto> customers;
    @BeforeEach
    void setUp(){
        this.customers=List.of(
                CustomerDto.builder().id(1L).firstName("mehdi").lastName("njema").email("mehdi@gmail.com").build(),
                CustomerDto.builder().id(2L).firstName("mohamed").lastName("rjeb").email("mohamed@gmail.com").build(),
                CustomerDto.builder().id(3L).firstName("fatma").lastName("iman").email("fatma@gmail.com").build());
    }

    @Test
    void shouldGetAllCustomers() throws Exception {
        Mockito.when(customerService.getAllCustomers()).thenReturn(customers);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customers)));
    }


    @Test
    void shouldGetCustomerById() throws Exception {
        Long customerId=1L;
        Mockito.when(customerService.getCustomersById(customerId)).thenReturn(customers.get(0));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/{id}",customerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customers.get(0))));
    }

    @Test
    void shouldNotGetCustomerById() throws Exception {
        Long customerId=9L;
        Mockito.when(customerService.getCustomersById(customerId)).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/{id}",customerId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
    @Test
    void shouldNotGetCustomerByInvalidId() throws Exception {
        Long id = 9L;
        Mockito.when(customerService.getCustomersById(id)).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void shouldSearchCustomers() throws Exception {
        String keyword="e";
        List<CustomerDto> expected= List.of(customers.get(0), customers.get(1));
        Mockito.when(customerService.searchCustomers(keyword)).thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/search?keyword="+keyword))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected)));}
    @Test
    void shouldSaveCustomer() throws Exception {
        CustomerDto customerDto=customers.get(0);
        String expected= """
                {
                  "id":1, "firstName"="mehdi", "lastName"="njema", "email"="mehdi@gmail.com"
                }
                """;
        Mockito.when(customerService.saveCustomer(Mockito.any())).thenReturn(customers.get(0));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void shouldUpdateCustomer() throws Exception{
        Long customerId=1L;
        CustomerDto customerDto=customers.get(0);
        Mockito.when(customerService.updateCustomer(Mockito.eq(customerId),Mockito.any())).thenReturn(customerDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/{id}",customerId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customerDto)));
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        Long customerId=1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/{id}",customerId))
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}
