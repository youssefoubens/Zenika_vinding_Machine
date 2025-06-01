package com.zenika.vendingmachine.controllers;

import com.zenika.vendingmachine.dtos.ProductDTO;
import com.zenika.vendingmachine.dtos.TransactionResponseDTO;
import com.zenika.vendingmachine.services.VendingMachineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/vending")
@Tag(name = "Vending Machine", description = "APIs for vending machine operations")
public class VendingMachineController {

    private final VendingMachineService vendingMachineService;

    @Autowired
    public VendingMachineController(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    @Operation(summary = "Get available products", description = "Returns a list of available products in the vending machine.")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully.")
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAvailableProducts() {
        return ResponseEntity.ok(vendingMachineService.getAvailableProducts());
    }

    @Operation(summary = "Insert a coin", description = "Insert a coin to increase the current balance.")
    @ApiResponse(responseCode = "200", description = "Coin inserted successfully.")
    @PostMapping("/insert-coin")
    public ResponseEntity<Void> insertCoin(
            @Parameter(description = "The amount of the coin to insert") @RequestParam double amount) {
        vendingMachineService.insertCoin(amount);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Select a product", description = "Select a product to be purchased.")
    @ApiResponse(responseCode = "200", description = "Product selected successfully.")
    @PostMapping("/select-product")
    public ResponseEntity<Void> selectProduct(
            @Parameter(description = "The ID of the product") @RequestParam Long productId,
            @Parameter(description = "Quantity to purchase") @RequestParam(defaultValue = "1") int quantity) {
        vendingMachineService.selectProduct(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Complete purchase", description = "Finalize the purchase and get transaction details.")
    @ApiResponse(responseCode = "200", description = "Purchase completed successfully.")
    @PostMapping("/purchase")
    public ResponseEntity<TransactionResponseDTO> completePurchase() {
        return ResponseEntity.ok(vendingMachineService.completePurchase());
    }

    @Operation(summary = "Cancel transaction", description = "Cancel the ongoing transaction.")
    @ApiResponse(responseCode = "200", description = "Transaction cancelled successfully.")
    @PostMapping("/cancel")
    public ResponseEntity<TransactionResponseDTO> cancelTransaction() {
        return ResponseEntity.ok(vendingMachineService.cancelTransaction());
    }

    @Operation(summary = "Get current balance", description = "Get the amount of money currently inserted.")
    @ApiResponse(responseCode = "200", description = "Balance retrieved successfully.")
    @GetMapping("/balance")
    public ResponseEntity<Double> getCurrentBalance() {
        return ResponseEntity.ok(vendingMachineService.getCurrentBalance());
    }

    @Operation(summary = "Get transaction status", description = "Retrieve the status and details of a transaction by ID.")
    @ApiResponse(responseCode = "200", description = "Transaction status retrieved successfully.")
    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionStatus(
            @Parameter(description = "ID of the transaction") @PathVariable Long id) {
        return ResponseEntity.ok(vendingMachineService.getTransactionStatus(id));
    }
}
