package org.springframework.demo.geekshop.controller.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.demo.geekshop.config.GenerateSwaggerDoc;
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
@Slf4j
@GenerateSwaggerDoc
@Api(value = "CatalogBrandController", tags = "CatalogBrandController")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found")})
public class CatalogBrandRestController {
    private CatalogBrandRepository catalogBrandRepository;

    public CatalogBrandRestController(CatalogBrandRepository catalogBrandRepository) {
        this.catalogBrandRepository = catalogBrandRepository;
    }

    @GetMapping
    @ApiOperation(value = "Gets the list of all CatalogBrands", response = List.class)
    ResponseEntity<List<CatalogBrand>> getAllCatalogBrands() {
        log.info("Received a GET request to get all catalogBrands.");
        List<CatalogBrand> brands = catalogBrandRepository.findAll();
        return ResponseEntity.ok(brands);
    }

    @PostMapping
    @ApiOperation(value = "Adds a new CatalogBrand", response = CatalogBrand.class)
    ResponseEntity<CatalogBrand> addCatalogBrand(@RequestBody CatalogBrand catalogBrand) {
        log.info("Received a POST request to add a new catalogBrand : {}.", catalogBrand);
        CatalogBrand catalogBrandSaved = catalogBrandRepository.save(catalogBrand);
        return ResponseEntity.ok(catalogBrandSaved);
    }

    @PutMapping
    @ApiOperation(value = "Updates an existing CatalogBrand instance", response = CatalogBrand.class)
    ResponseEntity<CatalogBrand> updateCatalogBrand(@RequestBody CatalogBrand catalogBrand) {
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
    @ApiOperation(value = "Deletes a CatalogBrand with the given id", response = Void.class)
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
