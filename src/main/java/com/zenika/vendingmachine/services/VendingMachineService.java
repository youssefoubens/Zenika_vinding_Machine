package com.zenika.vendingmachine.services;

import com.zenika.vendingmachine.dtos.ProductDTO;
import com.zenika.vendingmachine.dtos.TransactionResponseDTO;

import java.util.List;

public interface VendingMachineService {
    List<ProductDTO> getAvailableProducts();
    void insertCoin(double amount);
    void selectProduct(Long productId, int quantity);
    TransactionResponseDTO completePurchase();
    TransactionResponseDTO cancelTransaction();
    double getCurrentBalance();
    TransactionResponseDTO getTransactionStatus(Long transactionId);
}
