package org.springframework.demo.geekshop.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.demo.geekshop.config.GenerateSwaggerDoc;
import org.springframework.demo.geekshop.domain.CatalogItem;
import org.springframework.demo.geekshop.repository.CatalogItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.demo.geekshop.config.ApiConstants.*;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
@Slf4j
@GenerateSwaggerDoc
@Api(value = "CatalogItemController", tags = "CatalogItemController")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found")})
public class CatalogItemController {
    private final CatalogItemRepository catalogItemRepository;

    @GetMapping(ADMIN + CATALOG_ITEMS)
    @JsonView(RestView.Admin.class)
    @ApiOperation(value = "Gets the list of all CatalogItems for Admin", response = List.class)
    ResponseEntity<List<CatalogItem>> getAllCatalogItemsAdmin() {
        return getAllCatalogItems();
    }

    @PostMapping(ADMIN + CATALOG_ITEMS)
    @ApiOperation(value = "Adds a new CatalogItem", response = Void.class)
    ResponseEntity<Void> saveCatalogItem(@RequestBody CatalogItem catalogItem) {
        log.info("Received a POST request to save a new catalogItem : {}", catalogItem);
        if (catalogItem.getId() != null) {
            return new ResponseEntity("An attempt to save a persistent catalogItem is made.", HttpStatus.BAD_REQUEST);
        }
        CatalogItem savedItem = catalogItemRepository.save(catalogItem);
        if (savedItem != null) {
            log.info("The catalogItem is saved successfully.");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping(ADMIN + CATALOG_ITEMS)
    @ApiOperation(value = "Updates an existing CatalogItem", response = Void.class)
    ResponseEntity<Void> updateCatalogItem(@RequestBody CatalogItem catalogItem) {
        log.info("Received a PUT request to update a new catalogItem : {}", catalogItem);
        if (catalogItem.getId() == null) {
            return new ResponseEntity("An attempt to update catalogItem that does not exist in database has been made.",
                    HttpStatus.BAD_REQUEST);
        }
        CatalogItem savedItem = catalogItemRepository.save(catalogItem);
        if (savedItem != null) {
            log.info("The catalogItem is updated successfully.");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping(CATALOG_ITEMS)
    @JsonView(RestView.NormalUser.class)
    @ApiOperation(value = "Gets the list of all CatalogItems for a normal user", response = List.class)
    ResponseEntity<List<CatalogItem>> getAllCatalogItemsNormalUser() {
        return getAllCatalogItems();
    }

    @GetMapping(CATALOG_ITEMS + "/withname/{name}")
    @JsonView(RestView.NormalUser.class)
    @ApiOperation(value = "Gets a list of all CatalogItems matching the search text", response = List.class)
    ResponseEntity<List<CatalogItem>> getCatalogItemsWithName(@PathVariable String name) {
        log.info("Received a GET request to search for catalogItems with name : {}", name);

        List<CatalogItem> items = catalogItemRepository.findByNameContaining(name);
        if (CollectionUtils.isEmpty(items)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping(CATALOG_ITEMS + "/type/{catalogTypeId}/brand/{catalogBrandId}")
    @ApiOperation(value = "Gets a list of all CatalogItems for the given catalogTypeId and catalogBrandId", response = List.class)
    @JsonView(RestView.NormalUser.class)
    ResponseEntity<List<CatalogItem>> getCatalogItemsByTypeIdAndBrandId(
            @PathVariable Long catalogTypeId,
            @PathVariable Long catalogBrandId) {
        log.info("Received a GET request to search for catalogItems with catalogTypeId : {} and catalogBrandId : {}",
                catalogTypeId, catalogBrandId);
        List<CatalogItem> items = catalogItemRepository.findByCatalogType_IdAndCatalogBrand_Id(
                catalogTypeId, catalogBrandId);
        if (CollectionUtils.isEmpty(items)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    private ResponseEntity<List<CatalogItem>> getAllCatalogItems() {
        List<CatalogItem> allItems = catalogItemRepository.findAll();
        return ResponseEntity.ok(allItems);
    }
}
