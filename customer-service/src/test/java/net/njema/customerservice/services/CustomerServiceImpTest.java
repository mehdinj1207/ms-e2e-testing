package net.njema.customerservice.services;

import net.njema.customerservice.dtos.CustomerDto;
import net.njema.customerservice.entities.Customer;
import net.njema.customerservice.exceptions.CustomerNotFoundException;
import net.njema.customerservice.exceptions.EmailAlreadyExistsException;
import net.njema.customerservice.mappers.CustomerMapper;
import net.njema.customerservice.repositories.CustomerRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
class CustomerServiceImpTest {
    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerMapper customerMapper;
    @InjectMocks
    CustomerServiceImp underTest;

    @Test
    void shouldSaveNewCustomer() {
        CustomerDto customerDto=CustomerDto.builder().firstName("mehdi").lastName("njema").email("mehdi@gmail.com").build();
        Customer customer=Customer.builder().firstName("mehdi").lastName("njema").email("mehdi@gmail.com").build();
        Customer savedCustomer=Customer.builder().id(1L).firstName("mehdi").lastName("njema").email("mehdi@gmail.com").build();
        CustomerDto expected=CustomerDto.builder().id(1L).firstName("mehdi").lastName("njema").email("mehdi@gmail.com").build();

        Mockito.when(customerRepository.findByEmail(customerDto.getEmail())).thenReturn(Optional.empty());
        Mockito.when(customerMapper.fromCustomerDto(customerDto)).thenReturn(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(savedCustomer);
        Mockito.when(customerMapper.fromCustomer(savedCustomer)).thenReturn(expected);
        CustomerDto result=underTest.saveCustomer(customerDto);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
        AssertionsForClassTypes.assertThat(result).isNotNull();
    }
    @Test
    void shouldNotSaveNewCustomerWhenEmailNotExists(){
        CustomerDto customerDto=CustomerDto.builder().firstName("mehdi").lastName("njema").email("mehdi@gmail.com")
                .build();
        Customer customer = Customer.builder().id(5L).firstName("mehdi").lastName("njema").email("mehdi@gmail.com")
                .build();
        Mockito.when(customerRepository.findByEmail(customerDto.getEmail())).thenReturn(Optional.of(customer));
        AssertionsForClassTypes.assertThatThrownBy(()->underTest.saveCustomer(customerDto))
                .isInstanceOf(EmailAlreadyExistsException.class);
    }

    @Test
    void shouldGetAllCustomers() {
        List<CustomerDto> expected=List.of(
                CustomerDto.builder().id(1L).firstName("mehdi").lastName("njema").email("mehdi@gmail.com")
                .build(),
                CustomerDto.builder().id(1L).firstName("sami").lastName("rjeb").email("mehdi@gmail.com")
                        .build()
                );
        List<Customer> customers=List.of(
                Customer.builder().id(1L).firstName("mehdi").lastName("njema").email("mehdi@gmail.com")
                        .build(),
                Customer.builder().id(1L).firstName("sami").lastName("rjeb").email("mehdi@gmail.com")
                        .build()
        );
        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        Mockito.when(customerMapper.fromListCustomers(customers)).thenReturn(expected);
        List<CustomerDto> result = underTest.getAllCustomers();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void shouldFindCustomersById() {
        Long customerId=1L;
        Customer customer=Customer.builder().id(1L).firstName("sami").lastName("rjeb").email("mehdi@gmail.com")
                .build();
        CustomerDto expected = CustomerDto.builder().id(1L).firstName("sami").lastName("rjeb").email("mehdi@gmail.com")
                .build();
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Mockito.when(customerMapper.fromCustomer(customer)).thenReturn(expected);
        CustomerDto result= underTest.getCustomersById(customerId);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);

    }
    @Test
    void shouldNotFindCustomersById() {
        Long customerId=1L;
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        AssertionsForClassTypes.assertThatThrownBy(()->underTest.getCustomersById(customerId))
                .isInstanceOf(CustomerNotFoundException.class);

    }

    @Test
    void shouldSearchCustomers() {
        String keyword="e";
        List<CustomerDto> expected=List.of(
                CustomerDto.builder().firstName("mehdi").lastName("njema").email("mehdi@gmail.com")
                        .build(),
                CustomerDto.builder().firstName("sami").lastName("rjeb").email("mehdi@gmail.com")
                        .build()
        );
        List<Customer> customers=List.of(
                Customer.builder().firstName("mehdi").lastName("njema").email("mehdi@gmail.com")
                        .build(),
                Customer.builder().firstName("sami").lastName("rjeb").email("mehdi@gmail.com")
                        .build()
        );
        Mockito.when(customerRepository.findByFirstNameContainsIgnoreCase(keyword)).thenReturn(customers);
        Mockito.when(customerMapper.fromListCustomers(customers)).thenReturn(expected);
        List<CustomerDto> result = underTest.searchCustomers(keyword);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void shouldUpdateCustomer() {
        Long customerId=1L;
        CustomerDto customerDto=CustomerDto.builder().id(1L).firstName("sami").lastName("rjeb").email("mehdi@gmail.com")
                .build();
        Customer customer=Customer.builder().id(1L).firstName("sami").lastName("rjeb").email("mehdi@gmail.com")
                .build();
        Customer updatedCustomer=Customer.builder().id(1L).firstName("sami").lastName("rjeb").email("mehdi@gmail.com")
                .build();
        CustomerDto expected=CustomerDto.builder().id(1L).firstName("sami").lastName("rjeb").email("mehdi@gmail.com")
                .build();
        Mockito.when(customerRepository.findById(customerDto.getId())).thenReturn(Optional.of(customer));
        Mockito.when(customerMapper.fromCustomerDto(customerDto)).thenReturn(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(updatedCustomer);
        Mockito.when(customerMapper.fromCustomer(updatedCustomer)).thenReturn(expected);
        CustomerDto result = underTest.updateCustomer(customerId,customerDto);
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);

    }



    @Test
    void shouldDeleteCustomer() {
        Long customerId=1L;
        Customer customer = Customer.builder().id(1L).firstName("mehdi").lastName("njema").email("mehdi@gmail.com")
                .build();
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        underTest.deleteCustomer(customerId);
        Mockito.verify(customerRepository).deleteById(customerId);
    }
    @Test
    void shouldNotDeleteCustomer() {
        Long customerId=1L;
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        AssertionsForClassTypes.assertThatThrownBy(()->underTest.deleteCustomer(customerId)).isInstanceOf(CustomerNotFoundException.class);
    }
}