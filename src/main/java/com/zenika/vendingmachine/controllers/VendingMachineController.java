package com.zenika.vendingmachine.controllers;

import com.zenika.vendingmachine.dtos.ProductDTO;
import com.zenika.vendingmachine.dtos.TransactionResponseDTO;
import com.zenika.vendingmachine.services.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vending")
public class VendingMachineController {

    private final VendingMachineService vendingMachineService;

    @Autowired
    public VendingMachineController(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAvailableProducts() {
        return ResponseEntity.ok(vendingMachineService.getAvailableProducts());
    }

    @PostMapping("/insert-coin")
    public ResponseEntity<Void> insertCoin(@RequestParam double amount) {
        vendingMachineService.insertCoin(amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/select-product")
    public ResponseEntity<Void> selectProduct(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity) {
        vendingMachineService.selectProduct(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/purchase")
    public ResponseEntity<TransactionResponseDTO> completePurchase() {
        return ResponseEntity.ok(vendingMachineService.completePurchase());
    }

    @PostMapping("/cancel")
    public ResponseEntity<TransactionResponseDTO> cancelTransaction() {
        return ResponseEntity.ok(vendingMachineService.cancelTransaction());
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> getCurrentBalance() {
        return ResponseEntity.ok(vendingMachineService.getCurrentBalance());
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionStatus(@PathVariable Long id) {
        return ResponseEntity.ok(vendingMachineService.getTransactionStatus(id));
    }
}