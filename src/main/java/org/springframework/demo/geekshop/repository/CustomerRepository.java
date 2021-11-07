package org.springframework.demo.geekshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.demo.geekshop.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
