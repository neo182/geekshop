package org.springframework.demo.geekshop.service;

import org.springframework.demo.geekshop.domain.BasketItem;
import org.springframework.demo.geekshop.domain.CustomerBasket;

public interface BasketService {
    // Adds a new item into the basket
    BasketItem addBasketItem(BasketItem basketItem, Long customerId);
    // Increases the quantity of basketItem by 1
    BasketItem increaseBasketItem(Long basketItemId);
    // Decreases the quantity of basketItem by 1
    BasketItem decreaseBasketItem(Long basketItemId);
    // Get the basket for the given customerId
    CustomerBasket getBasketByCustomerId(Long customerId);
    // Removes the basket for the given customerId
    void removeCustomerBasket(Long customerId);
}
