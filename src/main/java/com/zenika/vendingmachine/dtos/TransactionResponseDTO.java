package com.zenika.vendingmachine.dtos;

import com.zenika.vendingmachine.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
    private List<TransactionItemResponse> items;
    private double totalAmount;
    private double amountReceived;
    private double changeGiven;
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private Map<String, Integer> changeBreakdown; // e.g., {"TEN_DH": 1, "FIVE_DH": 1}

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