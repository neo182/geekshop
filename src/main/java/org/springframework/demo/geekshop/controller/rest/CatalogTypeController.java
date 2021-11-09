package org.springframework.demo.geekshop.controller.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.demo.geekshop.config.GenerateSwaggerDoc;
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
@GenerateSwaggerDoc
@Api(value = "CatalogTypeController", tags = "CatalogTypeController")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found")})
public class CatalogTypeController {
    private final CatalogTypeRepository CatalogTypeRepository;

    @GetMapping
    @ApiOperation(value = "Gets the list of all CatalogTypes for Admin", response = List.class)
    ResponseEntity<List<CatalogType>> getAllCatalogTypes() {
        log.info("Received a GET request to get all catalogTypes.");
        List<CatalogType> brands = CatalogTypeRepository.findAll();
        return ResponseEntity.ok(brands);
    }

    @PostMapping
    @ApiOperation(value = "Adds a new CatalogType", response = Void.class)
    ResponseEntity<CatalogType> addCatalogType(@RequestBody CatalogType CatalogType) {
        log.info("Received a POST request to add a new CatalogType : {}.", CatalogType);
        CatalogType CatalogTypeSaved = CatalogTypeRepository.save(CatalogType);
        return ResponseEntity.ok(CatalogTypeSaved);
    }

    @PutMapping
    @ApiOperation(value = "Updates an existing CatalogType", response = Void.class)
    ResponseEntity<CatalogType> updateCatalogType(@RequestBody CatalogType catalogType) {
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
    @ApiOperation(value = "Deletes a CatalogType with the given id", response = Void.class)
    ResponseEntity<Void> deleteCatalogType(@PathVariable Long id) {
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