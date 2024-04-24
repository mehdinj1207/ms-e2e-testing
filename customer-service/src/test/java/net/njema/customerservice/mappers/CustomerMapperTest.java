package net.njema.customerservice.mappers;

import net.njema.customerservice.dtos.CustomerDto;
import net.njema.customerservice.entities.Customer;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.CharSequenceUtils;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {
    CustomerMapper underTest=new CustomerMapper();
    @Test
    public void shouldMapCustomerToCustomerDto(){
        Customer givenCustomer= Customer.builder().id(1l).firstName("mehdi").lastName("njema").email("mehdi@gmail.com").build();
        CustomerDto expected= CustomerDto.builder().id(1l).firstName("mehdi").lastName("njema").email("mehdi@gmail.com").build();
        CustomerDto result = underTest.fromCustomer(givenCustomer);
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
        assertThat(result).isNotNull();
    }
    @Test
    public void shouldMapCustomerDtoToCustomer(){
        CustomerDto givenCustomerDto= CustomerDto.builder().id(1l).firstName("mehdi").lastName("njema").email("mehdi@gmail.com").build();
        Customer expected= Customer.builder().id(1l).firstName("mehdi").lastName("njema").email("mehdi@gmail.com").build();
        Customer result = underTest.fromCustomerDto(givenCustomerDto);
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
        assertThat(result).isNotNull();

    }
    @Test
    public void shouldMapListOfCustomersToListOfCustomersDto(){
        List<Customer> givenCustomers=List.of(
                Customer.builder().id(1L).firstName("Mohamed").lastName("Njema").email("mohamed@gmail.com").build(),
                Customer.builder().id(2L).firstName("Yassin").lastName("Rjab").email("yassin@gmail.com").build(),
                Customer.builder().id(3L).firstName("Imane").lastName("Hmed").email("imane@gmail.com").build());
        List<CustomerDto> expected=List.of(
                CustomerDto.builder().id(1L).firstName("Mohamed").lastName("Njema").email("mohamed@gmail.com").build(),
                CustomerDto.builder().id(2L).firstName("Yassin").lastName("Rjab").email("yassin@gmail.com").build(),
                CustomerDto.builder().id(3L).firstName("Imane").lastName("Hmed").email("imane@gmail.com").build());
        List<CustomerDto> result = underTest.fromListCustomers(givenCustomers);
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
        assertThat(result).isNotNull();
    }
    @Test
    public void shouldNotMapNullCustomerToCustomerDto(){
        Customer givenCustomer= null;
        CustomerDto expected= CustomerDto.builder().id(1l).firstName("mehdi").lastName("njema").email("mehdi@gmail.com").build();
        AssertionsForClassTypes.assertThatThrownBy(()->underTest.fromCustomer(givenCustomer))
                .isInstanceOf(IllegalArgumentException.class);

    }


}