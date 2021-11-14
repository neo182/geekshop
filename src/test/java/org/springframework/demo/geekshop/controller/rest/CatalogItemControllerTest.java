package org.springframework.demo.geekshop.controller.rest;

import static org.springframework.demo.geekshop.config.ApiConstants.ADMIN;
import static org.springframework.demo.geekshop.config.ApiConstants.API_V1;
import static org.springframework.demo.geekshop.config.ApiConstants.CATALOG_ITEMS;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.demo.geekshop.domain.CatalogBrand;
import org.springframework.demo.geekshop.domain.CatalogItem;
import org.springframework.demo.geekshop.domain.CatalogType;
import org.springframework.demo.geekshop.repository.CatalogItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureJsonTesters
@WebMvcTest(CatalogItemRestController.class)
class CatalogItemControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JacksonTester<List<CatalogItem>> jacksonTesterCatalogItemList;

    @Autowired
    JacksonTester<CatalogItem> jacksonTesterCatalogItem;

    @MockBean
    CatalogItemRepository catalogItemRepository;

    CatalogItemRestController catalogItemController;

    CatalogItem catalogItem;

    @BeforeEach
    public void setUp() {
        catalogItemController = new CatalogItemRestController(catalogItemRepository);
        jacksonTesterCatalogItemList = jacksonTesterCatalogItemList.forView(RestView.NormalUser.class);


        catalogItem = CatalogItem.builder()
            .catalogBrand(new CatalogBrand(1L,"Spring"))
            .catalogType(new CatalogType(2L, "Hoodie"))
            .name("Groovy Hoodie")
            .description("The coolest hoodie")
            .displayPrice(new BigDecimal("15"))
            .pictureFileName("groovyHoodie")
            .pictureUrl("1.jpg")
            .availableStock(500)
            .restockThreshold(50)
            .maxStockThreshold(1000)
            .build();
    }

    @Test
    public void verifyGetAllCategoryItems() throws Exception {

        List<CatalogItem> catalogItems = Arrays.asList(catalogItem);

        BDDMockito.given(catalogItemRepository.findAll())
            .willReturn(catalogItems);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.get(API_V1 + CATALOG_ITEMS)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getContentAsString())
            .isEqualTo(jacksonTesterCatalogItemList.write(catalogItems).getJson());
    }

    @Test
    public void verifySaveCatalogItem() throws Exception {
        BDDMockito.given(catalogItemRepository.save(ArgumentMatchers.any()))
            .willReturn(catalogItem);

        System.out.println(jacksonTesterCatalogItem.write(catalogItem).getJson());

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.post(API_V1 + ADMIN + CATALOG_ITEMS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterCatalogItem.write(catalogItem).getJson()))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getStatus())
            .isEqualTo(HttpStatus.OK.value());
        Mockito.verify(catalogItemRepository).save(catalogItem);

    }

    @Test
    public void verifyUpdateCatalogItem() throws Exception {
        // Not recommended, done just for testing purpose
        catalogItem.setId(1L);
        BDDMockito.given(catalogItemRepository.save(ArgumentMatchers.any()))
            .willReturn(catalogItem);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.put(API_V1 + ADMIN + CATALOG_ITEMS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterCatalogItem.write(catalogItem).getJson()))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getStatus())
            .isEqualTo(HttpStatus.OK.value());
        Mockito.verify(catalogItemRepository).save(catalogItem);
    }
}