package net.njema.customerservice.services;

import net.njema.customerservice.dtos.CustomerDto;
import net.njema.customerservice.exceptions.CustomerNotFoundException;
import net.njema.customerservice.exceptions.EmailAlreadyExistsException;
import java.util.List;

public interface CustomerService {
    CustomerDto saveCustomer(CustomerDto customerDto)throws EmailAlreadyExistsException;
    List<CustomerDto> getAllCustomers();
    CustomerDto getCustomersById(Long id) throws CustomerNotFoundException;
    List<CustomerDto> searchCustomers(String keyword);
    CustomerDto updateCustomer(Long id,CustomerDto customerDto) throws CustomerNotFoundException;
    void deleteCustomer(Long id)throws CustomerNotFoundException;
}
