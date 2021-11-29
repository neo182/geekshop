package org.springframework.demo.geekshop.web.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.demo.geekshop.domain.CatalogBrand;
import org.springframework.demo.geekshop.repository.CatalogBrandRepository;

import java.beans.PropertyEditorSupport;

@Slf4j
public class CatalogBrandEditor extends PropertyEditorSupport {
    CatalogBrandRepository catalogBrandRepository;

    public CatalogBrandEditor(CatalogBrandRepository catalogBrandRepository) {
        this.catalogBrandRepository = catalogBrandRepository;
    }

    @Override
    public void setAsText(String id) {
        CatalogBrand catalogBrand = catalogBrandRepository.findById(Long.parseLong(id)).get();
        log.info("CatalogBrand : {}", catalogBrand);
        this.setValue(catalogBrand);
    }
}
