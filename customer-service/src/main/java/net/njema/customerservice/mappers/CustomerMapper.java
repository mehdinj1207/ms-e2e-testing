package net.njema.customerservice.mappers;

import net.njema.customerservice.dtos.CustomerDto;
import net.njema.customerservice.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {
    private ModelMapper modelMapper=new ModelMapper();
    public CustomerDto fromCustomer(Customer customer){
        return modelMapper.map(customer,CustomerDto.class);
    }
    public Customer fromCustomerDto(CustomerDto customerDto){
        return modelMapper.map(customerDto, Customer.class);
    }
    public List<CustomerDto> fromListCustomers(List<Customer> customers){
        return customers.stream().map(c->modelMapper.map(c,CustomerDto.class)).collect(Collectors.toList());
    }


}
