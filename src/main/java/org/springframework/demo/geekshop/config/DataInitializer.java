package org.springframework.demo.geekshop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.demo.geekshop.domain.CatalogBrand;
import org.springframework.demo.geekshop.domain.CatalogItem;
import org.springframework.demo.geekshop.domain.CatalogType;
import org.springframework.demo.geekshop.repository.CatalogBrandRepository;
import org.springframework.demo.geekshop.repository.CatalogItemRepository;
import org.springframework.demo.geekshop.repository.CatalogTypeRepository;

import java.math.BigDecimal;

@Slf4j
@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner setupCatalogItemData(CatalogBrandRepository brandRepository,
                                                  CatalogTypeRepository catalogTypeRepository,
                                                  CatalogItemRepository catalogItemRepository) {
        return (args) -> {
            // save few CatalogBrand
            CatalogBrand brandJava = brandRepository.save(new CatalogBrand("Java"));
            CatalogBrand brandSpring = brandRepository.save(new CatalogBrand("Spring"));
            CatalogBrand brandGroovy = brandRepository.save(new CatalogBrand("Groovy"));

            // save few CatalogType
            CatalogType hoodie = catalogTypeRepository.save(new CatalogType("Hoodie"));
            CatalogType mug = catalogTypeRepository.save(new CatalogType("Mug"));
            catalogTypeRepository.save(new CatalogType("T-shirt"));
            catalogTypeRepository.save(new CatalogType("Jacket"));
            catalogTypeRepository.save(new CatalogType("Cap"));
            catalogTypeRepository.save(new CatalogType("Sticker"));
            catalogTypeRepository.save(new CatalogType("Socks"));
            catalogTypeRepository.save(new CatalogType("Backpack"));

            catalogItemRepository.save(CatalogItem.builder()
                    .catalogBrand(brandGroovy)
                    .catalogType(hoodie)
                    .name("Groovy Hoodie")
                    .description("The coolest hoodie")
                    .displayPrice(new BigDecimal("20"))
                    .pictureFileName("blackLevis")
                    .pictureUrl("1.jpg")
                    .availableStock(500)
                    .restockThreshold(50)
                    .maxStockThreshold(1000)
                    .build());
            catalogItemRepository.save(CatalogItem.builder()
                    .catalogBrand(brandJava)
                    .catalogType(mug)
                    .name("Java Black Mug")
                    .description("The coolest java mugs")
                    .displayPrice(new BigDecimal("10"))
                    .pictureFileName("javamugblack")
                    .pictureUrl("2.jpg")
                    .availableStock(1000)
                    .restockThreshold(100)
                    .maxStockThreshold(2000)
                    .build());
        };
    }
}
