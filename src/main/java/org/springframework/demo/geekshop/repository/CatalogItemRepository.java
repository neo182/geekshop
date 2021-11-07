package org.springframework.demo.geekshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.demo.geekshop.domain.CatalogItem;

import java.util.List;

public interface CatalogItemRepository extends CrudRepository<CatalogItem, Long> {
    @Override
    List<CatalogItem> findAll();

    // Used for searching
    List<CatalogItem> findByNameContaining(String name);

    /*
    This method below can be implemented with the use of PropertyExpression in an easier way.
    See findByCatalogType_IdAndCatalogBrand_Id()
    @Query("SELECT item FROM CatalogItem item WHERE item.catalogType.id = :catalogTypeId and " +
            "item.catalogBrand.id = :catalogBrandId")
    List<CatalogItem> findByTypeIdAndBrandId(
            @Param("catalogTypeId") Long catalogTypeId,
            @Param("catalogBrandId") Long catalogBrandId);
    */
    List<CatalogItem> findByCatalogType_IdAndCatalogBrand_Id(
            Long catalogTypeId,
            Long catalogBrandId);

    // A good example of PropertyExpression with the use of _ to differentiate the property
    List<CatalogItem> findByCatalogBrand_Id(Long catalogBrandId);
}
