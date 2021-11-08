package org.springframework.demo.geekshop.controller.rest;

import static org.springframework.demo.geekshop.config.ApiConstants.API_V1;
import static org.springframework.demo.geekshop.config.ApiConstants.CATALOG_BRANDS;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import org.springframework.demo.geekshop.repository.CatalogBrandRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureJsonTesters
@WebMvcTest(CatalogBrandController.class)
class CatalogBrandControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JacksonTester<List<CatalogBrand>> jacksonTesterCatalogBrandList;

    @Autowired
    JacksonTester<CatalogBrand> jacksonTesterCatalogBrand;

    @MockBean
    CatalogBrandRepository catalogBrandRepository;

    CatalogBrandController catalogBrandController;

    @BeforeEach
    public void setup() {
        catalogBrandController = new CatalogBrandController(catalogBrandRepository);
    }

    @Test
    public void verifyGetAllCatalogBrands() throws Exception {
        List<CatalogBrand> catalogBrands = Arrays.asList(
            new CatalogBrand(1L, "Java"),
            new CatalogBrand(2L, "Groovy")
        );

        BDDMockito.given(catalogBrandRepository.findAll()).willReturn(catalogBrands);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.get(API_V1 + CATALOG_BRANDS)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getContentAsString())
            .isEqualTo(jacksonTesterCatalogBrandList.write(catalogBrands).getJson());
    }

    @Test
    public void verifyAddCatalogBrand() throws Exception {
        CatalogBrand brandJava = new CatalogBrand(1L, "Java");

        BDDMockito.given(catalogBrandRepository.save(ArgumentMatchers.any())).willReturn(brandJava);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.post(API_V1 + CATALOG_BRANDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterCatalogBrand.write(brandJava).getJson())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getContentAsString())
            .isEqualTo(jacksonTesterCatalogBrand.write(brandJava).getJson());
    }

    @Test
    public void verifyModifyCatalogBrand() throws Exception {
        CatalogBrand brandJava = new CatalogBrand(1L, "Java");

        BDDMockito.given(catalogBrandRepository.findById(ArgumentMatchers.any())).willReturn(Optional.of(brandJava));
        BDDMockito.given(catalogBrandRepository.save(ArgumentMatchers.any())).willReturn(brandJava);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.put(API_V1 + CATALOG_BRANDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterCatalogBrand.write(brandJava).getJson())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getContentAsString())
            .isEqualTo(jacksonTesterCatalogBrand.write(brandJava).getJson());
    }

    @Test
    public void verifyDeleteCatalogBrand() throws Exception {
        CatalogBrand brandJava = new CatalogBrand(1L, "Java");
        String brandId = brandJava.getId().toString();

        BDDMockito.given(catalogBrandRepository.findById(ArgumentMatchers.any()))
            .willReturn(Optional.of(brandJava));

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders
                .delete(API_V1 + CATALOG_BRANDS + "/" + brandId)
            )
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        Mockito.verify(catalogBrandRepository).delete(brandJava);
    }
}