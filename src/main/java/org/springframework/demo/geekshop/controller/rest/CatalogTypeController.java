package org.springframework.demo.geekshop.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.demo.geekshop.domain.CatalogType;
import org.springframework.demo.geekshop.repository.CatalogTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.demo.geekshop.config.ApiConstants.API_V1;
import static org.springframework.demo.geekshop.config.ApiConstants.CATALOG_TYPES;

@RestController
@RequestMapping(API_V1 + CATALOG_TYPES)
@RequiredArgsConstructor
@Slf4j
public class CatalogTypeController {
    private final CatalogTypeRepository CatalogTypeRepository;

    @GetMapping
    ResponseEntity<List<CatalogType>> getAllCatalogTypes() {
        log.info("Received a GET request to get all catalogTypes.");
        List<CatalogType> brands = CatalogTypeRepository.findAll();
        return ResponseEntity.ok(brands);
    }

    @PostMapping
    ResponseEntity<CatalogType> addCatalogType(@RequestBody CatalogType CatalogType) {
        log.info("Received a POST request to add a new CatalogType : {}.", CatalogType);
        CatalogType CatalogTypeSaved = CatalogTypeRepository.save(CatalogType);
        return ResponseEntity.ok(CatalogTypeSaved);
    }

    @PutMapping
    ResponseEntity<CatalogType> modifyCatalogType(@RequestBody CatalogType catalogType) {
        log.info("Received a PUT request to modify a catalogType : {}.", catalogType);

        Optional<CatalogType> optCatalogType = CatalogTypeRepository.findById(catalogType.getId());
        if (optCatalogType.isPresent()) {
            CatalogType CatalogTypeSaved = CatalogTypeRepository.save(catalogType);
            return ResponseEntity.ok(CatalogTypeSaved);
        } else {
            return new ResponseEntity("The referred CatalogType does not exit", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    ResponseEntity<CatalogType> deleteCatalogType(@PathVariable Long id) {
        log.info("Received a DELETE request to delete a catalogType with id : {}.", id);

        Optional<CatalogType> optCatalogType = CatalogTypeRepository.findById(id);
        if (optCatalogType.isPresent()) {
            CatalogTypeRepository.delete(optCatalogType.get());
            log.info("The CatalogType with id : {} is deleted.", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity("The referred CatalogType does not exit", HttpStatus.BAD_REQUEST);
        }
    }
}