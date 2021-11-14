package org.springframework.demo.geekshop.controller.rest;

import static org.springframework.demo.geekshop.config.ApiConstants.API_V1;
import static org.springframework.demo.geekshop.config.ApiConstants.CATALOG_TYPES;

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
import org.springframework.demo.geekshop.domain.CatalogType;
import org.springframework.demo.geekshop.repository.CatalogTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureJsonTesters
@WebMvcTest(CatalogTypeRestController.class)
class CatalogTypeRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JacksonTester<List<CatalogType>> jacksonTesterCatalogTypeList;

    @Autowired
    JacksonTester<CatalogType> jacksonTesterCatalogType;

    @MockBean
    CatalogTypeRepository catalogTypeRepository;

    CatalogTypeRestController catalogTypeRestController;

    @BeforeEach
    public void setup() {
        catalogTypeRestController = new CatalogTypeRestController(catalogTypeRepository);
    }

    @Test
    public void verifyGetAllCatalogTypes() throws Exception {
        List<CatalogType> CatalogTypes = Arrays.asList(
            new CatalogType(1L, "T-shirt"),
            new CatalogType(2L, "Sticker")
        );

        BDDMockito.given(catalogTypeRepository.findAll()).willReturn(CatalogTypes);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.get(API_V1 + CATALOG_TYPES)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getContentAsString())
            .isEqualTo(jacksonTesterCatalogTypeList.write(CatalogTypes).getJson());
    }

    @Test
    public void verifyAddCatalogType() throws Exception {
        CatalogType typeTShirt = new CatalogType(1L, "T-shirt");

        BDDMockito.given(catalogTypeRepository.save(ArgumentMatchers.any())).willReturn(typeTShirt);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.post(API_V1 + CATALOG_TYPES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterCatalogType.write(typeTShirt).getJson())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getContentAsString())
            .isEqualTo(jacksonTesterCatalogType.write(typeTShirt).getJson());
    }

    @Test
    public void verifyModifyCatalogType() throws Exception {
        CatalogType typeTShirt = new CatalogType(1L, "T-shirt");

        BDDMockito.given(catalogTypeRepository.findById(ArgumentMatchers.any())).willReturn(Optional.of(typeTShirt));
        BDDMockito.given(catalogTypeRepository.save(ArgumentMatchers.any())).willReturn(typeTShirt);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders.put(API_V1 + CATALOG_TYPES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterCatalogType.write(typeTShirt).getJson())
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getContentAsString())
            .isEqualTo(jacksonTesterCatalogType.write(typeTShirt).getJson());
    }

    @Test
    public void verifyDeleteCatalogType() throws Exception {
        CatalogType typeTShirt = new CatalogType(1L, "T-shirt");
        String id = typeTShirt.getId().toString();

        BDDMockito.given(catalogTypeRepository.findById(ArgumentMatchers.any()))
            .willReturn(Optional.of(typeTShirt));

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders
                .delete(API_V1 + CATALOG_TYPES + "/" + id)
            )
            .andReturn().getResponse();

        BDDAssertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        Mockito.verify(catalogTypeRepository).delete(typeTShirt);
    }

}