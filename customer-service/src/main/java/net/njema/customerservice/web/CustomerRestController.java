package net.njema.customerservice.web;

import jakarta.validation.Valid;
import net.njema.customerservice.dtos.CustomerDto;
import net.njema.customerservice.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
    private CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/customers")
    public List<CustomerDto> getCustomers(){
        return customerService.getAllCustomers();
    }
    @GetMapping("/customers/{id}")
    public CustomerDto getCustomerById(@PathVariable Long id){
        return customerService.getCustomersById(id);
    }
    @GetMapping("/customers/search")
    public List<CustomerDto> searchCustomers(@RequestParam String keyword){
        return customerService.searchCustomers(keyword);
    }
    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto saveCustomer(@RequestBody @Valid CustomerDto customerDto){
        return customerService.saveCustomer(customerDto);
    }
    @PutMapping("/customers/{id}")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable Long id){
        return customerService.updateCustomer(id,customerDto);
    }
    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(Long id){
        customerService.deleteCustomer(id);
    }
}
