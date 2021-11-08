package org.springframework.demo.geekshop.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.demo.geekshop.domain.CatalogBrand;
import org.springframework.demo.geekshop.repository.CatalogBrandRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.demo.geekshop.config.ApiConstants.*;

@RestController
@RequestMapping(API_V1 + CATALOG_BRANDS)
@RequiredArgsConstructor
@Slf4j
public class CatalogBrandController {
    private final CatalogBrandRepository catalogBrandRepository;

    @GetMapping
    ResponseEntity<List<CatalogBrand>> getAllCatalogBrands() {
        log.info("Received a GET request to get all catalogBrands.");
        List<CatalogBrand> brands = catalogBrandRepository.findAll();
        return ResponseEntity.ok(brands);
    }

    @PostMapping
    ResponseEntity<CatalogBrand> addCatalogBrand(@RequestBody CatalogBrand catalogBrand) {
        log.info("Received a POST request to add a new catalogBrand : {}.", catalogBrand);
        CatalogBrand catalogBrandSaved = catalogBrandRepository.save(catalogBrand);
        return ResponseEntity.ok(catalogBrandSaved);
    }

    @PutMapping
    ResponseEntity<CatalogBrand> modifyCatalogBrand(@RequestBody CatalogBrand catalogBrand) {
        log.info("Received a PUT request to modify a catalogBrand : {}.", catalogBrand);
        Optional<CatalogBrand> optCatalogBrand = catalogBrandRepository.findById(catalogBrand.getId());
        if (optCatalogBrand.isPresent()) {
            CatalogBrand catalogBrandSaved = catalogBrandRepository.save(catalogBrand);
            return ResponseEntity.ok(catalogBrandSaved);
        } else {
            return new ResponseEntity("The referred catalogBrand does not exit", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCatalogBrand(@PathVariable Long id) {
        log.info("Received a DELETE request to delete a catalogBrand with id : {}.", id);
        Optional<CatalogBrand> optCatalogBrand = catalogBrandRepository.findById(id);
        if (optCatalogBrand.isPresent()) {
            catalogBrandRepository.delete(optCatalogBrand.get());
            log.info("The CatalogBrand with id : {} is deleted.", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity("The referred catalogBrand does not exit", HttpStatus.BAD_REQUEST);
        }
    }
}
