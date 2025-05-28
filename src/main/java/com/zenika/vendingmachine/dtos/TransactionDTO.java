package com.zenika.vendingmachine.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * DTO for Transaction responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private List<TransactionItemDTO> items;
    private double amountInserted;

    // Constructors, getters, setters
}