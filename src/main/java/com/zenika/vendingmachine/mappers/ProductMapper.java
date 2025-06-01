package com.zenika.vendingmachine.mappers;

import com.zenika.vendingmachine.dtos.ProductDTO;
import com.zenika.vendingmachine.entities.Product;

import org.mapstruct.*;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.List;


@Component
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Converts Product entity to ProductDTO with purchasability check
     */
    @Mapping(target = "available", expression = "java(product.isPurchasable(currentBalance))")
    ProductDTO toDto(Product product, BigDecimal currentBalance);

    /**
     * Converts Product entity to ProductDTO without balance check
     */
    @Mapping(target = "available", expression = "java(product.getStockQuantity() > 0)")
    ProductDTO toDto(Product product);

    /**
     * Converts list of Product entities to list of ProductDTOs
     */
    List<ProductDTO> toDtoList(List<Product> products);



}
