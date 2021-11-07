package org.springframework.demo.geekshop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.demo.geekshop.domain.Order;
import org.springframework.demo.geekshop.domain.OrderItem;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query("SELECT order.items FROM Order order WHERE order.customer.id = :customerId")
    Set<OrderItem> findOrderItemsByCustomerId(@Param("customerId") Long customerId);

    List<Order> findByCustomerIdOrderByOrderedDateAsc(Long id);
}
