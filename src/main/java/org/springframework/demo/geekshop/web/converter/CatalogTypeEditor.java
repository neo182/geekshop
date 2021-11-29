package org.springframework.demo.geekshop.web.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.demo.geekshop.domain.CatalogType;
import org.springframework.demo.geekshop.repository.CatalogTypeRepository;

import java.beans.PropertyEditorSupport;

@Slf4j
public class CatalogTypeEditor extends PropertyEditorSupport {
    CatalogTypeRepository catalogTypeRepository;

    public CatalogTypeEditor(CatalogTypeRepository catalogTypeRepository) {
        this.catalogTypeRepository = catalogTypeRepository;
    }

    @Override
    public void setAsText(String id) {
        CatalogType catalogType = catalogTypeRepository.findById(Long.parseLong(id)).get();
        log.info("catalogType : {}", catalogType);
        this.setValue(catalogType);
    }
}

