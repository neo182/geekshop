package org.springframework.demo.geekshop.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.demo.geekshop.domain.CatalogBrand;
import org.springframework.demo.geekshop.domain.CatalogItem;
import org.springframework.demo.geekshop.domain.CatalogType;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@SpringBootTest
@ActiveProfiles("inmemory")
class CatalogItemRepositoryTest {
    @Autowired
    CatalogBrandRepository brandRepository;
    @Autowired
    CatalogTypeRepository catalogTypeRepository;
    @Autowired
    CatalogItemRepository catalogItemRepository;

    @Test
    public void verifyFindByTypeIdAndBrandId() {
        CatalogBrand brandJava = brandRepository.save(new CatalogBrand("Java"));
        CatalogBrand brandSpring = brandRepository.save(new CatalogBrand("Spring"));

        // save few CatalogType
        CatalogType catalogHoodie = catalogTypeRepository.save(new CatalogType("Hoodie"));
        CatalogType catalogMug = catalogTypeRepository.save(new CatalogType("Mug"));

        CatalogItem javaHoodie = catalogItemRepository.save(CatalogItem.builder()
                .catalogBrand(brandJava)
                .catalogType(catalogHoodie)
                .name("Java Hoodie")
                .description("The coolest hoodie")
                .displayPrice(new BigDecimal("1000"))
                .pictureFileName("JavaHoodie_White")
                .pictureUrl("1.jpg")
                .availableStock(500)
                .restockThreshold(50)
                .maxStockThreshold(1000)
                .build());

        CatalogItem springMug = catalogItemRepository.save(CatalogItem.builder()
                .catalogBrand(brandSpring)
                .catalogType(catalogMug)
                .name("Spring Mug Green")
                .description("The coolest mug")
                .displayPrice(new BigDecimal("1000"))
                .pictureFileName("SpringMug_Green")
                .pictureUrl("2.jpg")
                .availableStock(1000)
                .restockThreshold(100)
                .maxStockThreshold(2000)
                .build());

        List<CatalogItem> catalogItems1 = catalogItemRepository.findByCatalogType_IdAndCatalogBrand_Id(catalogHoodie.getId(), brandJava.getId());
        BDDAssertions.assertThat(catalogItems1.size()).isEqualTo(1);
        BDDAssertions.assertThat(catalogItems1.get(0).getId()).isEqualTo(javaHoodie.getId());

        List<CatalogItem> catalogItems2 = catalogItemRepository.findByCatalogType_IdAndCatalogBrand_Id(catalogMug.getId(), brandSpring.getId());
        BDDAssertions.assertThat(catalogItems2.size()).isEqualTo(1);
        BDDAssertions.assertThat(catalogItems2.get(0).getId()).isEqualTo(springMug.getId());

        List<CatalogItem> catalogItemsEmpty = catalogItemRepository.findByCatalogType_IdAndCatalogBrand_Id(100L, 40L);
        BDDAssertions.assertThat(catalogItemsEmpty.size()).isEqualTo(0);
    }

    @Test
    public void verifyFindByBrandId() {
        CatalogBrand brandJava = brandRepository.save(new CatalogBrand("Java"));

        // save few CatalogType
        CatalogType catalogHoodie = catalogTypeRepository.save(new CatalogType("Hoodie"));
        CatalogType catalogMug = catalogTypeRepository.save(new CatalogType("Mug"));

        catalogItemRepository.save(CatalogItem.builder()
                .catalogBrand(brandJava)
                .catalogType(catalogHoodie)
                .name("Java Hoodie")
                .description("The coolest hoodie")
                .displayPrice(new BigDecimal("1000"))
                .pictureFileName("JavaHoodie_White")
                .pictureUrl("1.jpg")
                .availableStock(500)
                .restockThreshold(50)
                .maxStockThreshold(1000)
                .build());

        catalogItemRepository.save(CatalogItem.builder()
                .catalogBrand(brandJava)
                .catalogType(catalogMug)
                .name("Java Mug Green")
                .description("The coolest mug")
                .displayPrice(new BigDecimal("1000"))
                .pictureFileName("JavaMug_Green")
                .pictureUrl("2.jpg")
                .availableStock(1000)
                .restockThreshold(100)
                .maxStockThreshold(2000)
                .build());

        List<CatalogItem> catalogItems1 = catalogItemRepository.findByCatalogBrand_Id(brandJava.getId());
        BDDAssertions.assertThat(catalogItems1.size()).isEqualTo(2);

        List<CatalogItem> catalogItemsEmpty = catalogItemRepository.findByCatalogBrand_Id(100L);
        BDDAssertions.assertThat(catalogItemsEmpty.size()).isEqualTo(0);
    }
}