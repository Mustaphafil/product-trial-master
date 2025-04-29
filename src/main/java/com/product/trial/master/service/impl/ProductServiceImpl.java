package com.product.trial.master.service.impl;

import com.product.trial.master.dto.ProductDto;
import com.product.trial.master.entities.Product;
import com.product.trial.master.entities.User;
import com.product.trial.master.enums.InventoryStatus;
import com.product.trial.master.repository.ProductRepository;
import com.product.trial.master.repository.UserRepository;
import com.product.trial.master.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        logger.info("--- START SAVING A NEW PRODUCT ---");
        if (productDto.id() != null) {
            logger.info("--- PRODUCT ID EXISTS, UPDATING PRODUCT WITH ID: {} ---", productDto.id());
            return updateProduct(productDto);
        } else {
            logger.info("--- NO PRODUCT ID PROVIDED, CREATING A NEW PRODUCT. ---");
        }
        Product product = map(productDto);
        logger.info("--- MAPPING COMPLETE, READY TO SAVE THE PRODUCT. ---");
        try {
            Product savedProduct = productRepository.save(product);
            logger.info("--- PRODUCT SAVED SUCCESSFULLY WITH ID: {} --- ", savedProduct.getId());
            return map(savedProduct);
        } catch (Exception e) {
            logger.error("--- ERROR WHILE SAVING PRODUCT: {} ---", e.getMessage());
            throw new RuntimeException("--- FAILED TO SAVE PRODUCT ---", e);
        } finally {
            logger.info("--- END OF SAVING A NEW PRODUCT ---");
        }
    }

    public ProductDto updateProduct(ProductDto productDto){
        logger.info("--- START UPDATING A PRODUCT WITH ID {} ---",productDto.id());
        Optional<Product> existingProduct = productRepository.findById(productDto.id());
        if (existingProduct.isPresent()){
            Product updatedProduct = productRepository.save(map(productDto));
            return map(updatedProduct);
        }else{
            logger.error("--- PRODUCT WITH ID {} NOT FOUND. ---", productDto.id());
            throw new RuntimeException("--- PRODUCT NOT FOUND.---");
        }

    }

    @Override
    public List<ProductDto> getAllProducts() {
        logger.info("--- START GETTING ALL PRODUCTS ---");
        List<Product> products = productRepository.findAll().stream().toList();
        if(products.isEmpty()){
            logger.info("--- THERE IS NO PRODUCT FOUND ---");
            return Collections.emptyList();
        }
        logger.info("--- END GETTING ALL PRODUCTS ---");
        return products.stream().map(ProductServiceImpl::map).toList();
    }

    @Override
    public ProductDto getProductById(Long id) {
        logger.info("--- START GETTING PRODUCT BY ID {} ---", id);
        Product product = productRepository.findById(id).orElseThrow(() -> {
            logger.error("--- PRODUCT WITH ID {} NOT FOUND ---", id);
            return new RuntimeException("Product not found with ID " + id);
        });
        logger.info("--- SUCCESSFULLY RETRIEVED PRODUCT WITH ID {} ---", id);
        return map(product);
    }

    @Override
    public ProductDto deleteProduct(Long id) {
        logger.info("--- START DELETING PRODUCT BY ID {} ---", id);
        Product product = productRepository.findById(id).orElseThrow(() -> {
            logger.error("--- PRODUCT WITH ID {} NOT FOUND ---", id);
            return new RuntimeException("Product not found with ID " + id);
        });
        logger.info("--- SUCCESSFULLY DELETE PRODUCT WITH ID {} ---", id);
        productRepository.delete(product);
        return map(product);}

    @Override
    public void addProductToCart(Long userId, Long productId) {
        logger.info("--- START TO ADD PRODUCT TO CART --- [User ID: {}, Product ID: {}]", userId, productId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("--- USER NOT FOUND WITH ID: {} ---", userId);
                    return new EntityNotFoundException("--- USER NOT FOUND ---");
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.warn("--- PRODUCT NOT FOUND WITH ID: {} ---", productId);
                    return new EntityNotFoundException("--- PRODUCT NOT FOUND --- ");
                });

        user.addToCart(product);
        userRepository.save(user);

        logger.info("--- PRODUCT SUCCESSFULLY ADDED TO CART --- [User ID: {}, Product ID: {}]", userId, productId);
    }

    @Override
    public void addProductToWishList(Long userId, Long productId) {
        logger.info("--- START TO ADD PRODUCT TO WISHLIST --- [User ID: {}, Product ID: {}]", userId, productId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("--- USER NOT FOUND WITH ID: {} ---", userId);
                    return new EntityNotFoundException("User not found");
                });
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.warn("--- PRODUCT NOT FOUND WITH ID: {} ---", productId);
                    return new EntityNotFoundException("--- PRODUCT NOT FOUND ---");
                });
        user.addToWishList(product);
        userRepository.save(user);
        logger.info("--- PRODUCT SUCCESSFULLY ADDED TO WISHLIST --- [USER ID: {}, PRODUCT ID: {}] ---", userId, productId);

    }

    private static ProductDto map(Product product) {
        return new ProductDto(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getInternalReference(),
                product.getShellId(),
                product.getInventoryStatus() != null ? product.getInventoryStatus().name() : null,
                product.getRating(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    private static Product map(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.id());
        product.setCode(dto.code());
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setImage(dto.image());
        product.setCategory(dto.category());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setInternalReference(dto.internalReference());
        product.setShellId(dto.shellId());

        if (dto.inventoryStatus() != null) {
            product.setInventoryStatus(InventoryStatus.valueOf(dto.inventoryStatus()));
        }

        product.setRating(dto.rating());
        product.setCreatedAt(dto.createdAt());
        product.setUpdatedAt(dto.updatedAt());
        return product;
    }
}
