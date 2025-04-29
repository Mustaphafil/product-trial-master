package com.product.trial.master.dto;

public record ProductDto(
    Long id,
    String code,
    String name,
    String description,
    String image,
    String category,
    double price,
    int quantity,
    String internalReference,
    Long shellId,
    String inventoryStatus,
    double rating,
    long createdAt,
    long updatedAt
) {}
