package org.springframework.demo.geekshop.controller.rest;

import static org.springframework.demo.geekshop.config.ApiConstants.API_V1;
import static org.springframework.demo.geekshop.config.ApiConstants.CUSTOMER_BASKET;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
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
import org.springframework.demo.geekshop.domain.BasketItem;
import org.springframework.demo.geekshop.domain.CatalogBrand;
import org.springframework.demo.geekshop.domain.CatalogItem;
import org.springframework.demo.geekshop.domain.CatalogType;
import org.springframework.demo.geekshop.domain.Customer;
import org.springframework.demo.geekshop.domain.CustomerBasket;
import org.springframework.demo.geekshop.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureJsonTesters
@WebMvcTest(CustomerBasketRestController.class)
class CustomerBasketControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BasketService basketService;

    @Autowired
    JacksonTester<Set<BasketItem>> jacksonTesterBasketItemSet;

    @Autowired
    JacksonTester<BasketItem> jacksonTesterBasketItem;

    CustomerBasketRestController customerBasketController;

    private BasketItem basketItem;
    private CustomerBasket customerBasket;
    private Set<BasketItem> basketItems;

    @BeforeEach
    public void setUp() {
        customerBasketController = new CustomerBasketRestController(basketService);

        CatalogItem catalogItem = CatalogItem.builder()
            .catalogBrand(new CatalogBrand(1L, "Groovy"))
            .catalogType(new CatalogType(1L, "Hoodie"))
            .name("Groovy Hoodie")
            .description("The coolest hoodie")
            .displayPrice(new BigDecimal("1000"))
            .pictureFileName("groovie-hoodie")
            .pictureUrl("1.jpg")
            .availableStock(30)
            .restockThreshold(10)
            .maxStockThreshold(100)
            .build();

        Customer customer = Customer.builder()
            .id(1L)
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

        basketItem = BasketItem.builder()
            .catalogItem(catalogItem)
            .sellingPrice(new BigDecimal(100))
            .quantity(1)
            .build();
        basketItems = new HashSet<>();
        basketItems.add(basketItem);

        customerBasket = CustomerBasket.builder()
            .customer(customer)
            .items(basketItems)
            .build();
    }

    @Test
    public void verifyGetAllBasketItemsForCustomer() throws Exception {
        BDDMockito.given(basketService.getBasketByCustomerId(ArgumentMatchers.any()))
            .willReturn(customerBasket);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.get(API_V1 + CUSTOMER_BASKET + "/customerId/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getContentAsString())
            .isEqualTo(jacksonTesterBasketItemSet.write(basketItems).getJson());
    }

    @Test
    public void verifyAddBasketItem() throws Exception {
        BDDMockito.given(basketService.addBasketItem(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .willReturn(basketItem);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.post(API_V1 + CUSTOMER_BASKET + "/customerId/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterBasketItem.write(basketItem).getJson())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getContentAsString())
            .isEqualTo(jacksonTesterBasketItem.write(basketItem).getJson());
    }

    @Test
    public void increaseBasketItem() throws Exception {
        BDDMockito.given(basketService.increaseBasketItem(ArgumentMatchers.any()))
            .willReturn(basketItem);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.put(API_V1 + CUSTOMER_BASKET + "/increase/basketItemId/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getContentAsString())
            .isEqualTo(jacksonTesterBasketItem.write(basketItem).getJson());
    }

    @Test
    public void decreaseBasketItem() throws Exception {
        BDDMockito.given(basketService.decreaseBasketItem(ArgumentMatchers.any()))
            .willReturn(basketItem);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.put(API_V1 + CUSTOMER_BASKET + "/decrease/basketItemId/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getContentAsString())
            .isEqualTo(jacksonTesterBasketItem.write(basketItem).getJson());
    }

    @Test
    public void removeBasketItem() throws Exception {
        BDDMockito.doNothing().when(basketService).removeCustomerBasket(ArgumentMatchers.any());

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.delete(API_V1 + CUSTOMER_BASKET + "/customerId/1")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getStatus())
            .isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}