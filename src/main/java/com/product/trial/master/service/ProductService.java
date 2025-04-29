package com.product.trial.master.service;

import com.product.trial.master.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto saveProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    ProductDto deleteProduct(Long id);
    void addProductToCart(Long userId, Long productId);
    void addProductToWishList(Long userId, Long productId);
}
