package com.zenika.vendingmachine.services;

import com.zenika.vendingmachine.dtos.ProductDTO;
import com.zenika.vendingmachine.dtos.TransactionResponseDTO;
import com.zenika.vendingmachine.entities.Product;
import com.zenika.vendingmachine.entities.Transaction;
import com.zenika.vendingmachine.enums.TransactionStatus;

import com.zenika.vendingmachine.execptions.InsufficientFundsException;
import com.zenika.vendingmachine.execptions.ProductUnavailableException;
import com.zenika.vendingmachine.mappers.ProductMapper;
import com.zenika.vendingmachine.mappers.TransactionMapper;
import com.zenika.vendingmachine.repositories.ProductRepository;
import com.zenika.vendingmachine.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
        List<Product> availableProducts = productRepository.findByQuantityGreaterThan(0);
        return productMapper.toDtoList(availableProducts);
    }

    @Override
    public void insertCoin(double amount) {
        if (currentTransaction == null) {
            currentTransaction = Transaction.builder()
                    .amountReceived(amount)
                    .status(TransactionStatus.IN_PROGRESS)
                    .totalAmount().changeGiven().build();
        } else {
            currentTransaction.setAmountReceived(currentTransaction.getAmountReceived() + amount);
        }
    }

    @Override
    public void selectProduct(Long productId, int quantity) {
        if (currentTransaction == null) {
            currentTransaction = Transaction.builder()
                    .status(TransactionStatus.IN_PROGRESS)
                    .totalAmount().amountReceived().changeGiven().build();
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductUnavailableException("Product not found"));

        if (product.getStock() < quantity) {
            throw new ProductUnavailableException("Not enough stock for product: " + product.getName());
        }

        // Add product to transaction
        currentTransaction.addItem(product, quantity);
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

        if (currentTransaction.getAmountReceived() < totalAmount) {
            throw new InsufficientFundsException("Insufficient funds. Please insert more coins.");
        }

        // Update transaction details
        currentTransaction.setTotalAmount(totalAmount);
        currentTransaction.setChangeGiven(currentTransaction.getAmountReceived() - totalAmount);
        currentTransaction.setStatus(TransactionStatus.COMPLETED);
        currentTransaction.setTimestamp(LocalDateTime.now());

        // Update product quantities
        currentTransaction.getItems().forEach(item -> {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
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
        currentTransaction.setTimestamp(LocalDateTime.now());
        Transaction cancelledTransaction = transactionRepository.save(currentTransaction);
        currentTransaction = null; // Reset current transaction

        return transactionMapper.toResponseDto(cancelledTransaction);
    }

    @Override
    public double getCurrentBalance() {
        return currentTransaction != null ? currentTransaction.getAmountReceived() : 0;
    }

    @Override
    public TransactionResponseDTO getTransactionStatus(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        return transactionMapper.toResponseDto(transaction);
    }
}