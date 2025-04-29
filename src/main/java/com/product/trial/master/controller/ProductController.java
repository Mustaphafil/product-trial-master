package com.product.trial.master.controller;

import com.product.trial.master.constant.ApiEndpoints;
import com.product.trial.master.dto.ProductDto;
import com.product.trial.master.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.product.trial.master.constant.ApiEndpoints.*;

@RestController
@RequestMapping(ApiEndpoints.BASE_API)
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping( ApiEndpoints.SAVE)
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> saveProduct(@RequestBody ProductDto productDto) {
        try {
            ProductDto savedProduct = productService.saveProduct(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("--- AN ERROR OCCURRED WHILE SAVING THE PRODUCT. ---");
        }
    }

    @GetMapping( ApiEndpoints.FIND_ALL)
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> retrieveAllProducts() {
        try {
            List<ProductDto> products = productService.getAllProducts();
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("--- AN ERROR OCCURRED WHILE RETRIEVING ALL PRODUCTS. ---");
        }
    }

    @GetMapping(ApiEndpoints.PRODUCT_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            ProductDto productDto = productService.getProductById(id);
            return ResponseEntity.ok(productDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("--- PRODUCT NOT FOUND WITH ID: ---" + id);
        }
    }

    @PatchMapping(UPDATE)
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto) {
        try {
            ProductDto savedProduct = productService.updateProduct(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("--- AN ERROR OCCURRED WHILE UPDATING THE PRODUCT. ---");
        }
    }

    @DeleteMapping(REMOVE_PRODUCT_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeProduct(@PathVariable Long id) {
        try {
            ProductDto productDto = productService.deleteProduct(id);
            return ResponseEntity.ok(productDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("--- PRODUCT NOT FOUND WITH ID: ---" + id);
        }
    }

    @PostMapping(ADD_TO_CART)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToCart(@PathParam("idUser") Long idUser, @PathParam("idProduct") Long idProduct) {
        try {
            productService.addProductToCart(idUser, idProduct);
            return ResponseEntity.ok("--- PRODUCT ADDED TO CART SUCCESSFULLY. ---");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("--- PRODUCT OR USER NOT FOUND WITH ID: " + idProduct + " OR ---" + idUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("--- AN ERROR OCCURRED WHILE ADDING THE PRODUCT TO CART. ---");
        }
    }

    @PostMapping(ADD_TO_WISH_LIST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToWishList(@PathParam("idUser") Long idUser, @PathParam("idProduct") Long idProduct) {
        try {
            productService.addProductToWishList(idUser, idProduct);
            return ResponseEntity.ok("--- PRODUCT ADDED TO CART SUCCESSFULLY. ---");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("--- PRODUCT OR USER NOT FOUND WITH ID: " + idProduct + " OR ---" + idUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("--- AN ERROR OCCURRED WHILE ADDING THE PRODUCT TO CART. ---");
        }
    }

}
