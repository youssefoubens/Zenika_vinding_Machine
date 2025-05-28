package com.zenika.vendingmachine.services;

import com.zenika.vendingmachine.dtos.ProductDTO;
import com.zenika.vendingmachine.dtos.TransactionResponseDTO;
import com.zenika.vendingmachine.enums.Coin;

import java.util.List;
import java.util.Map;

public interface VendingMachineService {
    List<ProductDTO> getAvailableProducts();
    void insertCoin(double amount);
    void selectProduct(Long productId, int quantity);
    TransactionResponseDTO completePurchase();
    TransactionResponseDTO cancelTransaction();
    double getCurrentBalance();
    TransactionResponseDTO getTransactionStatus(Long transactionId);
    Map<Coin, Integer> calculateChangeBreakdown(double changeAmount);
}
