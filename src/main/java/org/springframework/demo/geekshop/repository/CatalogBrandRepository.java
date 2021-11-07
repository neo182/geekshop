package org.springframework.demo.geekshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.demo.geekshop.domain.CatalogBrand;

import java.util.List;

public interface CatalogBrandRepository extends CrudRepository<CatalogBrand, Long> {
    @Override
    List<CatalogBrand> findAll();
}
