package com.zenika.vendingmachine.dtos;

import com.zenika.vendingmachine.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionResponseDTO {
    private Long transactionId;
    private List<TransactionItemDTO> purchasedItems;
    private double totalAmount;
    private double changeGiven;
    private TransactionStatus status;
    private LocalDateTime timestamp;

    // Constructors, getters, setters
}