package org.springframework.demo.geekshop.controller.rest;

import static org.springframework.demo.geekshop.config.ApiConstants.API_V1;
import static org.springframework.demo.geekshop.config.ApiConstants.ORDER;

import java.util.List;
import java.util.Optional;

import org.springframework.demo.geekshop.domain.Customer;
import org.springframework.demo.geekshop.domain.Order;
import org.springframework.demo.geekshop.repository.CustomerRepository;
import org.springframework.demo.geekshop.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(API_V1 + ORDER)
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("CustomerId/{customerId}")
    ResponseEntity<List<Order>> getOrderItemsByCustomerId(@PathVariable("customerId") Long customerId) {
        log.info("Received a GET request to get order for the customer with Id : {}", customerId);

        Optional<Customer> optCustomer = customerRepository.findById(customerId);
        if (optCustomer.isEmpty()) {
            return new ResponseEntity(String.format("The customer with id : %s does not exist!", customerId),
                HttpStatus.BAD_REQUEST);
        }

        List<Order> orders = orderRepository.findByCustomerIdOrderByOrderedDateAsc(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
