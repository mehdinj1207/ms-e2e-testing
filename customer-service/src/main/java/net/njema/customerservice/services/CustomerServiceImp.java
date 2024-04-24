package net.njema.customerservice.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.njema.customerservice.dtos.CustomerDto;
import net.njema.customerservice.entities.Customer;
import net.njema.customerservice.exceptions.CustomerNotFoundException;
import net.njema.customerservice.exceptions.EmailAlreadyExistsException;
import net.njema.customerservice.mappers.CustomerMapper;
import net.njema.customerservice.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CustomerServiceImp implements CustomerService{
    CustomerMapper customerMapper;
    CustomerRepository customerRepository;

    public CustomerServiceImp(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) throws EmailAlreadyExistsException {
        log.info(String.format("Saving new customer %s",customerDto.toString()));
        Optional<Customer> byEmail=customerRepository.findByEmail(customerDto.getEmail());
        if(byEmail.isPresent()){
            log.error(String.format("This mail %s already ecists",customerDto.getEmail()));
            throw new EmailAlreadyExistsException();
        }
        Customer customerToSave = customerMapper.fromCustomerDto(customerDto);
        Customer savedCustomer = customerRepository.save(customerToSave);
        CustomerDto result= customerMapper.fromCustomer(savedCustomer);
        return result;


    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> allCustomers=customerRepository.findAll();
        return customerMapper.fromListCustomers(allCustomers);
    }

    @Override
    public CustomerDto getCustomersById(Long id) throws CustomerNotFoundException {
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isEmpty()){
            throw  new CustomerNotFoundException();
        }
        return customerMapper.fromCustomer(customer.get());
    }

    @Override
    public List<CustomerDto> searchCustomers(String keyword) {
        List<Customer> customers=customerRepository.findByFirstNameContainsIgnoreCase(keyword);
        return customerMapper.fromListCustomers(customers);
    }

    @Override
    public CustomerDto updateCustomer(Long id,CustomerDto customerDto) throws CustomerNotFoundException {
        Optional<Customer> byId = customerRepository.findById(customerDto.getId());
        if(byId.isEmpty()) throw new CustomerNotFoundException();
        Customer customerToUpdate=customerMapper.fromCustomerDto(customerDto);
        Customer updatedCustomer = customerRepository.save(customerToUpdate);
        return customerMapper.fromCustomer(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) throw new CustomerNotFoundException();
        customerRepository.deleteById(id);

    }
}
