package org.springframework.demo.geekshop.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.demo.geekshop.domain.*;
import org.springframework.demo.geekshop.repository.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@SpringBootTest
@ActiveProfiles("inmemory")
//@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class BasketServiceTest {
    @Autowired
    BasketService basketService;
    @Autowired
    private CatalogBrandRepository catalogBrandRepository;
    @Autowired
    private CatalogTypeRepository catalogTypeRepository;
    @Autowired
    private BasketItemRepository basketItemRepository;
    @Autowired
    private CatalogItemRepository catalogItemRepository;
    @Autowired
    private CustomerBasketRepository customerBasketRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private CatalogItem catalogItem;
    private Customer customer;

    @BeforeEach
    public void setupCatalogItem() {
        CatalogBrand brandGroovy = catalogBrandRepository.save(new CatalogBrand("Groovy"));
        CatalogType hoodie = catalogTypeRepository.save(new CatalogType("Hoodie"));

        catalogItem = catalogItemRepository.save(CatalogItem.builder()
                .catalogBrand(brandGroovy)
                .catalogType(hoodie)
                .name("Groovy Hoodie")
                .description("The coolest hoodie")
                .displayPrice(new BigDecimal("1000"))
                .pictureFileName("groovie-hoodie")
                .pictureUrl("1.jpg")
                .availableStock(30)
                .restockThreshold(10)
                .maxStockThreshold(100)
                .build());

        Customer testCustomer = Customer.builder()
                .email("testEmail")
                .password("testPassword")
                .firstName("FirstName")
                .lastName("LastName")
                .phoneNumber("23232323")
                .city("City")
                .street("Street")
                .country("Norway")
                .postcode("2132")
                .createdDate(LocalDate.now())
                .enabled(true)
                .build();

        customer = customerRepository.save(testCustomer);
    }

    @Test
    public void verifyGetCustomerBasketByBuyerId() {
        BasketItem basketItem1 = BasketItem.builder()
                .catalogItem(catalogItem)
                .sellingPrice(new BigDecimal(100))
                .quantity(1)
                .build();

        basketService.addBasketItem(basketItem1, customer.getId());
        CustomerBasket customerBasketSaved = basketService.getBasketByCustomerId(customer.getId());
        BDDAssertions.assertThat(customerBasketSaved.getCustomer()).isNotNull();
        BDDAssertions.assertThat(customerBasketSaved.getItems().size()).isEqualTo(1);
    }

    @Test
    public void verifyIncreaseBasketItem() {
        BasketItem basketItem1 = BasketItem.builder()
                .catalogItem(catalogItem)
                .sellingPrice(new BigDecimal(100))
                .quantity(3)
                .build();

        BasketItem basketItem = basketService.addBasketItem(basketItem1, customer.getId());
        basketService.increaseBasketItem(basketItem.getId());
        basketService.increaseBasketItem(basketItem.getId());

        CustomerBasket customerBasketSaved = basketService.getBasketByCustomerId(customer.getId());
        BDDAssertions.assertThat(customerBasketSaved.getCustomer()).isNotNull();
        BasketItem basketItemSaved = (BasketItem) customerBasketSaved.getItems().toArray()[0];
        BDDAssertions.assertThat(basketItemSaved.getQuantity()).isEqualTo(5);
    }

    @Test
    public void verifyDecreaseBasketItem() {
        BasketItem basketItem1 = BasketItem.builder()
                .catalogItem(catalogItem)
                .sellingPrice(new BigDecimal(100))
                .quantity(3)
                .build();

        BasketItem basketItem = basketService.addBasketItem(basketItem1, customer.getId());
        basketService.decreaseBasketItem(basketItem.getId());
        basketService.decreaseBasketItem(basketItem.getId());

        CustomerBasket customerBasketSaved = basketService.getBasketByCustomerId(customer.getId());
        BDDAssertions.assertThat(customerBasketSaved.getCustomer()).isNotNull();
        BasketItem basketItemSaved = (BasketItem) customerBasketSaved.getItems().toArray()[0];
        BDDAssertions.assertThat(basketItemSaved.getQuantity()).isEqualTo(1);
    }

    @Test
    public void verifyRemoveCustomerBasket() {
        BasketItem basketItem1 = BasketItem.builder()
                .catalogItem(catalogItem)
                .sellingPrice(new BigDecimal(100))
                .quantity(3)
                .build();

        basketService.addBasketItem(basketItem1, customer.getId());
        basketService.removeCustomerBasket(customer.getId());

        CustomerBasket customerBasketSaved = basketService.getBasketByCustomerId(customer.getId());
        BDDAssertions.assertThat(customerBasketSaved).isNull();
    }
}