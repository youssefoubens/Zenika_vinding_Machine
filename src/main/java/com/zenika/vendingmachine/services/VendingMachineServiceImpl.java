package com.zenika.vendingmachine.services;

import com.zenika.vendingmachine.dtos.ProductDTO;
import com.zenika.vendingmachine.dtos.TransactionResponseDTO;
import com.zenika.vendingmachine.entities.Product;
import com.zenika.vendingmachine.entities.Transaction;
import com.zenika.vendingmachine.entities.TransactionItem;
import com.zenika.vendingmachine.enums.Coin;
import com.zenika.vendingmachine.enums.TransactionStatus;
import com.zenika.vendingmachine.execptions.InsufficientFundsException;
import com.zenika.vendingmachine.execptions.ProductUnavailableException;
import com.zenika.vendingmachine.mappers.ProductMapper;
import com.zenika.vendingmachine.mappers.TransactionMapper;
import com.zenika.vendingmachine.repositories.ProductRepository;
import com.zenika.vendingmachine.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class VendingMachineServiceImpl implements VendingMachineService {

    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;
    private final ProductMapper productMapper;
    private final TransactionMapper transactionMapper;
    private Transaction currentTransaction;

    public VendingMachineServiceImpl(ProductRepository productRepository,
                                     TransactionRepository transactionRepository,
                                     ProductMapper productMapper,
                                     TransactionMapper transactionMapper) {
        this.productRepository = productRepository;
        this.transactionRepository = transactionRepository;
        this.productMapper = productMapper;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<ProductDTO> getAvailableProducts() {
        List<Product> availableProducts = productRepository.findByStockGreaterThan(0);
        return productMapper.toDtoList(availableProducts);
    }

    @Override
    public void insertCoin(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        BigDecimal amountBD = BigDecimal.valueOf(amount);
        if (!Coin.isValidCoinValue(amountBD)) {
            throw new IllegalArgumentException("Invalid coin value: " + amount);
        }
        if (currentTransaction == null) {
            currentTransaction = Transaction.builder()
                    .amountReceived(amount)
                    .status(TransactionStatus.IN_PROGRESS)
                    .items(new ArrayList<>())
                    .totalAmount(0.0)
                    .changeGiven(0.0)
                    .build();
        } else {
            currentTransaction.setAmountReceived(currentTransaction.getAmountReceived() + amount);
        }
    }

    @Override
    public void selectProduct(Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        if (currentTransaction == null) {
            currentTransaction = Transaction.builder()
                    .status(TransactionStatus.IN_PROGRESS)
                    .items(new ArrayList<>())
                    .amountReceived(0.0)
                    .totalAmount(0.0)
                    .changeGiven(0.0)
                    .build();
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductUnavailableException("Product not found"));

        if (product.getStock() < quantity) {
            throw new ProductUnavailableException("Not enough stock for product: " + product.getName());
        }

        // Check if product already exists in transaction
        Optional<TransactionItem> existingItem = currentTransaction.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            TransactionItem newItem = TransactionItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .unitPrice(product.getPrice())
                    .transaction(currentTransaction)
                    .build();
            currentTransaction.getItems().add(newItem);
        }
    }

    @Override
    public TransactionResponseDTO completePurchase() {
        if (currentTransaction == null || currentTransaction.getItems().isEmpty()) {
            throw new IllegalStateException("No transaction in progress or no products selected");
        }

        // Calculate total amount
        double totalAmount = currentTransaction.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        currentTransaction.setTotalAmount(totalAmount);

        if (!currentTransaction.canComplete()) {
            throw new InsufficientFundsException(
                    String.format("Insufficient funds. Required: %.2f, Inserted: %.2f",
                            totalAmount, currentTransaction.getAmountReceived())
            );
        }

        // Calculate and set change
        double change = currentTransaction.getRemainingBalance();
        currentTransaction.setChangeGiven(change);
        currentTransaction.setStatus(TransactionStatus.COMPLETED);

        // Update product quantities
        currentTransaction.getItems().forEach(item -> {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        });

        // Save transaction
        Transaction savedTransaction = transactionRepository.save(currentTransaction);
        currentTransaction = null; // Reset current transaction

        return transactionMapper.toResponseDto(savedTransaction);
    }

    @Override
    public TransactionResponseDTO cancelTransaction() {
        if (currentTransaction == null) {
            throw new IllegalStateException("No transaction in progress");
        }

        currentTransaction.setStatus(TransactionStatus.CANCELLED);
        Transaction cancelledTransaction = transactionRepository.save(currentTransaction);
        currentTransaction = null; // Reset current transaction

        return transactionMapper.toResponseDto(cancelledTransaction);
    }

    @Override
    public double getCurrentBalance() {
        return currentTransaction != null ? currentTransaction.getAmountReceived() : 0.0;
    }

    @Override
    public TransactionResponseDTO getTransactionStatus(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with id: " + transactionId));
        return transactionMapper.toResponseDto(transaction);
    }
}