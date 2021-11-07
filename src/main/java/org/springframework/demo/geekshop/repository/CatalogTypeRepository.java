package org.springframework.demo.geekshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.demo.geekshop.domain.CatalogType;

import java.util.List;

public interface CatalogTypeRepository extends CrudRepository<CatalogType, Long> {
    @Override
    List<CatalogType> findAll();
}
