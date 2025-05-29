package com.zenika.vendingmachine.mappers;

import com.zenika.vendingmachine.dtos.TransactionDTO;
import com.zenika.vendingmachine.dtos.TransactionItemDTO;
import com.zenika.vendingmachine.dtos.TransactionResponseDTO;
import com.zenika.vendingmachine.entities.Transaction;
import com.zenika.vendingmachine.entities.TransactionItem;
import org.mapstruct.*;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface for Transaction entity and DTOs
 */
@Component
@Mapper(componentModel = "spring")
public interface TransactionMapper {

    /**
     * Converts Transaction entity to TransactionDTO
     */
    @Mapping(target = "items", source = "items", qualifiedByName = "mapTransactionItemsToDTOs")
    TransactionDTO toDto(Transaction transaction);


    @Mapping(target = "items", source = "items", qualifiedByName = "mapItemsToResponse")
    TransactionResponseDTO toResponseDto(Transaction transaction);


    @Named("mapTransactionItemsToDTOs")
    default List<TransactionItemDTO> mapTransactionItemsToDTOs(List<TransactionItem> items) {
        if (items == null) {
            return null;
        }

        return items.stream()
                .map(item -> new TransactionItemDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),  // Use unitPrice from TransactionItem instead of product price
                        item.getTotalPrice()    // Use calculated total price
                ))
                .collect(Collectors.toList());
    }

    /**
     * Maps TransactionItems to TransactionItemResponse objects
     */
    @Named("mapItemsToResponse")
    default List<TransactionResponseDTO.TransactionItemResponse> mapItems(List<TransactionItem> items) {
        if (items == null) {
            return null;
        }

        return items.stream()
                .map(item -> TransactionResponseDTO.TransactionItemResponse.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())  // Use unitPrice from TransactionItem
                        .totalPrice(item.getTotalPrice()) // Use calculated total price
                        .build())
                .collect(Collectors.toList());
    }
}