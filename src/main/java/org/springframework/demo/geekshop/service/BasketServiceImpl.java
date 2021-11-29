package org.springframework.demo.geekshop.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.demo.geekshop.domain.BasketItem;
import org.springframework.demo.geekshop.domain.Customer;
import org.springframework.demo.geekshop.domain.CustomerBasket;
import org.springframework.demo.geekshop.repository.BasketItemRepository;
import org.springframework.demo.geekshop.repository.CustomerBasketRepository;
import org.springframework.demo.geekshop.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    @Autowired
    BasketItemRepository basketItemRepository;

    @Autowired
    CustomerBasketRepository customerBasketRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    @Transactional
    public BasketItem addBasketItem(final BasketItem basketItem, final Long customerId) {
        if (basketItem.getId() != null) {
            throw new IllegalStateException(String.format("Method called for a basketItem with id : %s!", basketItem));
        }

        CustomerBasket customerBasket = customerBasketRepository.findByCustomer_Id(customerId);
        if (customerBasket == null) {
            customerBasket = new CustomerBasket();
            Optional<Customer> optCustomer = customerRepository.findById(customerId);
            if (optCustomer.isEmpty()) {
                throw new IllegalStateException(String.format("The customer with id : %s does not exist!", customerId));
            }
            customerBasket.setCustomer(optCustomer.get());
        }

        customerBasket.addBasketItem(basketItem);
        customerBasketRepository.save(customerBasket);
        return basketItem;
    }

    @Override
    public CustomerBasket getBasketByCustomerId(final Long customerId) {
        return customerBasketRepository.findByCustomer_Id(customerId);
    }

    @Override
    public void removeCustomerBasket(final Long customerId) {
        customerBasketRepository.deleteByCustomer_Id(customerId);
    }

    @Override
    public BasketItem increaseBasketItem(final Long basketItemId) {
        Optional<BasketItem> optBasketItem = basketItemRepository.findById(basketItemId);
        if (!optBasketItem.isPresent()) {
            throw new UnsupportedOperationException("The item does not exist!");
        }

        BasketItem basketItem = optBasketItem.get();
        basketItem.increaseQuantity();
        return basketItemRepository.save(basketItem);
    }

    @Override
    public BasketItem decreaseBasketItem(final Long basketItemId) {
        Optional<BasketItem> optBasketItem = basketItemRepository.findById(basketItemId);
        if (!optBasketItem.isPresent()) {
            throw new UnsupportedOperationException("The item does not exist!");
        }

        BasketItem basketItem = optBasketItem.get();
        basketItem.decreaseQuantity();
        return basketItemRepository.save(basketItem);
    }
}

