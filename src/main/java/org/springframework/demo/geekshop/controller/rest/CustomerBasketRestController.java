package org.springframework.demo.geekshop.controller.rest;

import static org.springframework.demo.geekshop.config.ApiConstants.API_V1;
import static org.springframework.demo.geekshop.config.ApiConstants.CUSTOMER_BASKET;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.demo.geekshop.config.GenerateSwaggerDoc;
import org.springframework.demo.geekshop.domain.BasketItem;
import org.springframework.demo.geekshop.domain.CustomerBasket;
import org.springframework.demo.geekshop.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(API_V1 + CUSTOMER_BASKET)
@Slf4j
@GenerateSwaggerDoc
@Api(value = "CustomerBasketController", tags = "CustomerBasketController")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found")})
public class CustomerBasketRestController {
    private BasketService basketService;

    public CustomerBasketRestController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/customerId/{customerId}")
    @ApiOperation(value = "Gets the list of BasketItem for the given customerId", response = List.class)
    ResponseEntity<List<BasketItem>> getAllBasketItemsForCustomer(@PathVariable("customerId") Long customerId) {
        log.info("Received a GET request to get all basketItems.");
        CustomerBasket customerBasket = basketService.getBasketByCustomerId(customerId);
        if (customerBasket == null) {
            return new ResponseEntity(String.format("The shopping basket for the customer with id : %s catalogBrand does not exit", customerId),
                HttpStatus.BAD_REQUEST);
        }
        List<BasketItem> items = new ArrayList<>();
        items.addAll(customerBasket.getItems());
        return ResponseEntity.ok(items);
    }

    @PostMapping("/customerId/{customerId}")
    @ApiOperation(value = "Adds a new BasketItem for the given customerId", response = BasketItem.class)
    ResponseEntity<BasketItem> addBasketItem(@PathVariable("customerId") Long customerId,
        @RequestBody BasketItem basketItem) {
        log.info("Received a POST request to add a new basketItem : {}.", basketItem);
        BasketItem savedBasketItem = basketService.addBasketItem(basketItem, customerId);
        return ResponseEntity.ok(savedBasketItem);
    }

    @PutMapping("/increase/basketItemId/{basketItemId}")
    @ApiOperation(value = "Increases the quantitiy of the BasketItem with the given basketItemId", response = BasketItem.class)
    ResponseEntity<BasketItem> increaseBasketItem(@PathVariable("basketItemId") Long basketItemId) {
        log.info("Received a POST request to increase basketItem with id : {}.", basketItemId);
        try {
            BasketItem savedBasketItem = basketService.increaseBasketItem(basketItemId);
            return ResponseEntity.ok(savedBasketItem);
        } catch (Exception exception) {
            log.debug(String.format("Something went wrong in increasing the quantity of the given basketItem with id %s", basketItemId),
                exception);
            return new ResponseEntity("The quantity of basketItem could not be increased!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/decrease/basketItemId/{basketItemId}")
    @ApiOperation(value = "Decreases the quantitiy of the BasketItem with the given basketItemId", response = BasketItem.class)
    ResponseEntity<BasketItem> decreaseBasketItem(@PathVariable("basketItemId") Long basketItemId) {
        log.info("Received a POST request to decrease basketItem with id : {}.", basketItemId);
        try {
            BasketItem savedBasketItem = basketService.decreaseBasketItem(basketItemId);
            return ResponseEntity.ok(savedBasketItem);
        } catch (Exception exception) {
            log.debug(String.format("Something went wrong in decreasing the quantity of the given basketItem with id %s", basketItemId),
                exception);
            return new ResponseEntity("The quantity of basketItem could not be decrease!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/customerId/{customerId}")
    @ApiOperation(value = "Deletes the CustomerBasket for the the given customerId", response = Void.class)
    ResponseEntity<Void> removeCustomerBasket(@PathVariable("customerId") Long customerId) {
        log.info("Received a DELETE request to delete customerBasket for the customerId : {}.", customerId);
        basketService.removeCustomerBasket(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
