package org.springframework.demo.geekshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.demo.geekshop.domain.CustomerBasket;

import javax.transaction.Transactional;

public interface CustomerBasketRepository extends CrudRepository<CustomerBasket, Long> {
    /*
     With the proper use of Property expressions as described in (https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-property-expressions),
     one can implement the method below with a simple approach as in the method findByCustomer_Id()
     @Query("SELECT basket FROM CustomerBasket basket WHERE basket.customer.id = :customerId")
     CustomerBasket findByCustomerId(@Param("customerId") Long customerId);
    */
    CustomerBasket findByCustomer_Id(Long customerId);

    // Transaction is used here to delete the BasketItems
    @Transactional
    void deleteByCustomer_Id(Long customerId);
}
