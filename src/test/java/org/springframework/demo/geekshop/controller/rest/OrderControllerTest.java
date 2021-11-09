package org.springframework.demo.geekshop.controller.rest;

import static org.springframework.demo.geekshop.config.ApiConstants.API_V1;
import static org.springframework.demo.geekshop.config.ApiConstants.ORDER;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.demo.geekshop.domain.CatalogBrand;
import org.springframework.demo.geekshop.domain.CatalogItem;
import org.springframework.demo.geekshop.domain.CatalogType;
import org.springframework.demo.geekshop.domain.Customer;
import org.springframework.demo.geekshop.domain.Order;
import org.springframework.demo.geekshop.domain.OrderItem;
import org.springframework.demo.geekshop.repository.CustomerRepository;
import org.springframework.demo.geekshop.repository.OrderRepository;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureJsonTesters
@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JacksonTester<List<Order>> jacksonTesterOrders;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    OrderRepository orderRepository;

    OrderController orderController;
    Order order;

    @BeforeEach
    public void setUp() {
        orderController = new OrderController(orderRepository, customerRepository);

        CatalogItem catalogItem = CatalogItem.builder()
            .id(1L)
            .catalogBrand(new CatalogBrand("Groovy"))
            .catalogType(new CatalogType("Hoodie"))
            .name("Groovy Hoodie")
            .description("The coolest hoodie")
            .displayPrice(new BigDecimal("10"))
            .pictureFileName("groovie-hoodie")
            .pictureUrl("1.jpg")
            .availableStock(30)
            .restockThreshold(10)
            .maxStockThreshold(100)
            .build();

        Set<OrderItem> items = new HashSet<>();
        items.add(OrderItem.builder()
            .id(2L)
            .catalogItem(catalogItem)
            .soldPrice(new BigDecimal("7.5"))
            .quantity(1)
            .build());

        Customer customer = Customer.builder()
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

        order = Order.builder()
            .id(3L)
            .customer(customer)
            .items(items)
            .status("Shipped")
            .orderedDate(LocalDate.now())
            .shippingCost(new BigDecimal("2.5"))
            .paymentMethod("Credit Card")
            .totalSum(new BigDecimal("10"))
            .build();
    }

    @Test
    public void verifyGetOrderItemsByCustomerId() throws Exception {
        BDDMockito.given(customerRepository.findById(ArgumentMatchers.anyLong()))
            .willReturn(Optional.of(new Customer()));

        List<Order> orders = Arrays.asList(order);

        BDDMockito.given(orderRepository.findByCustomerIdOrderByOrderedDateAsc(ArgumentMatchers.any()))
            .willReturn(orders);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.get(API_V1 + ORDER + "/CustomerId/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterOrders.write(orders).getJson()))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getContentAsString())
            .isEqualTo(jacksonTesterOrders.write(orders).getJson());
    }

}