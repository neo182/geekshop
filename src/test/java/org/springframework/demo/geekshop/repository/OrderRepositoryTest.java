package org.springframework.demo.geekshop.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.demo.geekshop.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@SpringBootTest
@ActiveProfiles("inmemory")
class OrderRepositoryTest {
    @Autowired
    CatalogBrandRepository catalogBrandRepository;
    @Autowired
    CatalogTypeRepository catalogTypeRepository;
    @Autowired
    CatalogItemRepository catalogItemRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;

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
                .displayPrice(new BigDecimal("10"))
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
    public void verifyAddOrderItems() {
        OrderItem savedOrderItem = orderItemRepository.save(OrderItem.builder()
                .catalogItem(catalogItem)
                .soldPrice(new BigDecimal("7.5"))
                .quantity(1)
                .build());

        Set<OrderItem> items = new HashSet<>();
        items.add(savedOrderItem);
        Order savedOrder = orderRepository.save(Order.builder()
                .customer(customer)
                .items(items)
                .status("Shipped")
                .orderedDate(LocalDate.now())
                .shippingCost(new BigDecimal("1.25"))
                .paymentMethod("Credit Card")
                .build());

        Optional<Order> optOrder = orderRepository.findById(savedOrder.getId());
        BDDAssertions.assertThat(optOrder.get()).isNotNull();
    }

    @Test
    public void verifyFindByOrder_Customer_Id() {
        OrderItem savedOrderItem = orderItemRepository.save(OrderItem.builder()
                .catalogItem(catalogItem)
                .soldPrice(new BigDecimal("7.5"))
                .quantity(1)
                .build());

        Set<OrderItem> items = new HashSet<>();
        items.add(savedOrderItem);
        Order savedOrder = orderRepository.save(Order.builder()
                .customer(customer)
                .items(items)
                .status("Shipped")
                .orderedDate(LocalDate.now())
                .shippingCost(new BigDecimal("1.25"))
                .paymentMethod("Credit Card")
                .build());

        //Set<OrderItem> orders = orderRepository.findOrderItemsByCustomerId(customer.getId());
        List<Order> orders = orderRepository.findByCustomerIdOrderByOrderedDateAsc(customer.getId());
        BDDAssertions.assertThat(orders.size()).isEqualTo(1);
    }
}