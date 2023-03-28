package org.example.customersservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRepository customerRepository;
    @PostMapping
    public Customer create(@RequestBody Customer customer){
        return customerRepository.save(customer);
    }

}
