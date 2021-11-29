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
            CatalogType tshirt = catalogTypeRepository.save(new CatalogType("Tshirt"));
            CatalogType cup = catalogTypeRepository.save(new CatalogType("Cup"));
            catalogTypeRepository.save(new CatalogType("Hoodie"));
            catalogTypeRepository.save(new CatalogType("Jacket"));
            catalogTypeRepository.save(new CatalogType("Mug"));
            catalogTypeRepository.save(new CatalogType("Sticker"));
            catalogTypeRepository.save(new CatalogType("Socks"));
            catalogTypeRepository.save(new CatalogType("Backpack"));

            catalogItemRepository.save(CatalogItem.builder()
                    .catalogBrand(brandJava)
                    .catalogType(tshirt)
                    .name("Java T-shirt")
                    .description("Classic Java T-shirt")
                    .displayPrice(new BigDecimal("20"))
                    .pictureFileName("Java T-shirt(white)")
                    .pictureUrl("java_tshirt_white.jpg")
                    .availableStock(500)
                    .restockThreshold(50)
                    .maxStockThreshold(1000)
                    .build());
            catalogItemRepository.save(CatalogItem.builder()
                    .catalogBrand(brandJava)
                    .catalogType(cup)
                    .name("Java Cup")
                    .description("Classic Java Cup")
                    .displayPrice(new BigDecimal("5"))
                    .pictureFileName("Java Cup(white)")
                    .pictureUrl("java_cup_white.jpg")
                    .availableStock(1000)
                    .restockThreshold(100)
                    .maxStockThreshold(2000)
                    .build());
            catalogItemRepository.save(CatalogItem.builder()
                    .catalogBrand(brandJava)
                    .catalogType(cup)
                    .name("Java Developer Cup")
                    .description("Classic Java Developer Cup")
                    .displayPrice(new BigDecimal("5"))
                    .pictureFileName("Java Cup(black)")
                    .pictureUrl("java_cup_black.jpg")
                    .availableStock(1000)
                    .restockThreshold(100)
                    .maxStockThreshold(2000)
                    .build());
            catalogItemRepository.save(CatalogItem.builder()
                    .catalogBrand(brandSpring)
                    .catalogType(tshirt)
                    .name("Spring T-shirt")
                    .description("Classic Spring T-shirt")
                    .displayPrice(new BigDecimal("20"))
                    .pictureFileName("Spring T-shirt(white)")
                    .pictureUrl("spring_tshirt_white.jpg")
                    .availableStock(500)
                    .restockThreshold(50)
                    .maxStockThreshold(1000)
                    .build());
            catalogItemRepository.save(CatalogItem.builder()
                    .catalogBrand(brandSpring)
                    .catalogType(tshirt)
                    .name("New Spring T-shirt")
                    .description("Classic Spring T-shirt")
                    .displayPrice(new BigDecimal("20"))
                    .pictureFileName("Spring T-shirt(black)")
                    .pictureUrl("spring_tshirt_black.jpg")
                    .availableStock(500)
                    .restockThreshold(50)
                    .maxStockThreshold(1000)
                    .build());
        };
    }
}
