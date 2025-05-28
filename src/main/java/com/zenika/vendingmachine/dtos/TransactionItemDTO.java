package com.zenika.vendingmachine.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionItemDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;

    // Constructors, getters, setters
}