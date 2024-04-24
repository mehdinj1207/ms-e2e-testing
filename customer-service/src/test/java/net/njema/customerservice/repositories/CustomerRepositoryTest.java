package net.njema.customerservice.repositories;

import net.njema.customerservice.entities.Customer;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.org.checkerframework.framework.qual.DefaultQualifier;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @BeforeEach
    public void setUp(){
        System.out.println("**************************");
        customerRepository.save(Customer.builder().firstName("Mehdi").lastName("Njema").email("mehdi@gmail.com").build());
        customerRepository.save(Customer.builder().firstName("Mohamed").lastName("Njema").email("mohamed@gmail.com").build());
        customerRepository.save(Customer.builder().firstName("Yassin").lastName("Rjab").email("yassin@gmail.com").build());
        System.out.println("**************************");
    }
    @Test
    public void shouldFindCustomerByEmail(){
        String givenEmail="mehdi@gmail.com";
        Optional<Customer> result = customerRepository.findByEmail(givenEmail);
        assertThat(result).isPresent();

    }
    @Test
    public void shouldNotFindCustomerByEmail(){
        String givenEmail="mehdi1@gmail.com";
        Optional<Customer> result = customerRepository.findByEmail(givenEmail);
        assertThat(result).isEmpty();
    }
    @Test
    public void shouldFindCustomersByFirstName(){
        String keyword="e";
        List<Customer> expected= List.of(
                Customer.builder().firstName("Mehdi").lastName("Njema").email("mehdi@gmail.com").build(),
                Customer.builder().firstName("Mohamed").lastName("Njema").email("mohamed@gmail.com").build());
        List<Customer> result = customerRepository.findByFirstNameContainsIgnoreCase(keyword);
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(expected.size());
        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);

    }

}