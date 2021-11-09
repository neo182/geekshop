package org.springframework.demo.geekshop.controller.rest;

import static org.springframework.demo.geekshop.config.ApiConstants.API_V1;
import static org.springframework.demo.geekshop.config.ApiConstants.ORDER;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.demo.geekshop.config.GenerateSwaggerDoc;
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
@GenerateSwaggerDoc
@Api(value = "OrderController", tags = "OrderController")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found")})
public class OrderController {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("CustomerId/{customerId}")
    @ApiOperation(value = "Gets the list of all Orders made by a Customer with given id", response = List.class)
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
