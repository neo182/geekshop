package org.springframework.demo.geekshop.controller.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.demo.geekshop.domain.CatalogBrand;
import org.springframework.demo.geekshop.domain.CatalogItem;
import org.springframework.demo.geekshop.domain.CatalogType;
import org.springframework.demo.geekshop.domain.Customer;
import org.springframework.demo.geekshop.domain.Order;
import org.springframework.demo.geekshop.domain.OrderItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonSerialize(using = LocalDateTimeSerializer.class)
class OrderSerializerTest {
    private Order order;

    @BeforeEach
    public void setUpOrderAndOrderItem() {
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
    public void verifySerializationWithOrderSerializer() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<Order> orders = Arrays.asList(order, order);
        String ordersJson = mapper.writeValueAsString(orders);
        log.info("order : {}", ordersJson);

        DocumentContext jsonContext = JsonPath.parse(ordersJson);
        BDDAssertions.assertThat(jsonContext.read("$[0]['status']").toString()).isEqualTo("Shipped");
        BDDAssertions.assertThat(jsonContext.read("$[0]['totalSum']").toString()).isEqualTo("10");
        BDDAssertions.assertThat(jsonContext.read("$[0]['shippingCost']").toString()).isEqualTo("2.5");
        BDDAssertions.assertThat(jsonContext.read("$[0]['paymentMethod']").toString()).isEqualTo("Credit Card");

        BDDAssertions.assertThat(jsonContext.read("$[0]['orderItems'].length()").toString()).isEqualTo("1");
        BDDAssertions.assertThat(jsonContext.read("$[0]['orderItems'][0].orderItemId").toString()).isEqualTo("2");
        BDDAssertions.assertThat(jsonContext.read("$[0]['orderItems'][0].itemId").toString()).isEqualTo("1");
    }
}