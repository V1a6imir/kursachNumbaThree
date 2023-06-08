package kur3.server.controller;

import kur3.server.entity.Customer;
import kur3.server.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add-customer")
    public void addCust(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
    }

    @DeleteMapping("/delete-customer/{id}")
    public void deleteCust(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/data-customer")
    public List<Customer> getCustData() {
        return customerService.getAllCustomers();
    }

    @PutMapping("/edit-customer/{id}")
    public void editCust(@PathVariable Long id, @RequestBody Customer customer) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer != null) {
            existingCustomer.setName(customer.getName());
            existingCustomer.setAddress(customer.getAddress());
            existingCustomer.setLegalAddress(customer.getLegalAddress());
            // Update other fields as needed
            customerService.updateCustomer(existingCustomer);
        }
    }
}
