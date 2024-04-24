package net.njema.customerservice;

import net.njema.customerservice.entities.Customer;
import net.njema.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	@Profile("!test")
	CommandLineRunner start(CustomerRepository customerRepository){
		return  args -> {
			customerRepository.save(Customer.builder().firstName("Mehdi").lastName("Njema").email("mehdi@gmail.com").build());
			customerRepository.save(Customer.builder().firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build());
			customerRepository.save(Customer.builder().firstName("Hanane").lastName("yamal").email("hanane@gmail.com").build());
		};
	}
}
