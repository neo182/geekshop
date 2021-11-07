package org.springframework.demo.geekshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.demo.geekshop.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
