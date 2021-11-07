package org.springframework.demo.geekshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.demo.geekshop.domain.BasketItem;

import java.util.List;

public interface BasketItemRepository extends CrudRepository<BasketItem, Long> {
    @Override
    List<BasketItem> findAll();
}
