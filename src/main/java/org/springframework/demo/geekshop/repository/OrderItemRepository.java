package org.springframework.demo.geekshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.demo.geekshop.domain.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}
