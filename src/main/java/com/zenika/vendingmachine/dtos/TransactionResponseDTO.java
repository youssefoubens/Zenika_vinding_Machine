package com.zenika.vendingmachine.dtos;

import com.zenika.vendingmachine.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
    private List<TransactionItemResponse> items;
    private double totalAmount;
    private double amountInserted;
    private double changeReturned;
    private TransactionStatus status;
    private LocalDateTime timestamp;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionItemResponse {
        private Long productId;
        private String productName;
        private int quantity;
        private double unitPrice;
        private double totalPrice;
    }
}