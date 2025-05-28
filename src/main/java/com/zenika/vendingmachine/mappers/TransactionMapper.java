package com.zenika.vendingmachine.mappers;

import com.zenika.vendingmachine.dtos.TransactionDTO;
import com.zenika.vendingmachine.dtos.TransactionItemDTO;
import com.zenika.vendingmachine.entities.Transaction;
import com.zenika.vendingmachine.entities.TransactionItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface for Transaction entity and DTOs
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {

    /**
     * Converts Transaction entity to TransactionDTO
     */
    @Mapping(target = "items", expression = "java(mapTransactionItemsToDTOs(transaction.getItems()))")
    @Mapping(target = "amountInserted", source = "amountReceived")
    TransactionDTO toDto(Transaction transaction);

    /**
     * Converts list of Transaction entities to list of TransactionDTOs
     */
    List<TransactionDTO> toDtoList(List<Transaction> transactions);

    /**
     * Maps TransactionItems to TransactionItemDTOs
     */
    default List<TransactionItemDTO> mapTransactionItemsToDTOs(List<TransactionItem> items) {
        return items.stream()
                .map(item -> new TransactionItemDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice(),
                        item.getQuantity() * item.getProduct().getPrice()
                ))
                .collect(Collectors.toList());
    }
}