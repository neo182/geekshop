package org.springframework.demo.geekshop.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.demo.geekshop.domain.Order;
import org.springframework.demo.geekshop.domain.OrderItem;
import org.springframework.demo.geekshop.repository.OrderItemRepository;
import org.springframework.demo.geekshop.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.demo.geekshop.config.ApiConstants.*;

@RestController
@RequestMapping(API_V1 + ORDER)
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @GetMapping("CustomerId/{customerId}")
    ResponseEntity<List<Order>> getOrderItemsByCustomerId(@PathVariable("customerId") Long customerId) {
        List<Order> orders = orderRepository.findByCustomerIdOrderByOrderedDateAsc(customerId);
        //Todo : Write a serializer for Order that formats the children ie orderItems
        return null;
    }
}
