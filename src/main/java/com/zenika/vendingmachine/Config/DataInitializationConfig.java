package com.zenika.vendingmachine.Config;

import com.zenika.vendingmachine.entities.Product;
import com.zenika.vendingmachine.entities.Transaction;
import com.zenika.vendingmachine.entities.TransactionItem;
import com.zenika.vendingmachine.enums.TransactionStatus;
import com.zenika.vendingmachine.repositories.ProductRepository;
import com.zenika.vendingmachine.repositories.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializationConfig {

    @Bean
    public CommandLineRunner initializeData(ProductRepository productRepository,
                                            TransactionRepository transactionRepository) {
        return args -> {
            if (productRepository.count() == 0) { // Initialiser seulement si vide

                Product coke = Product.builder()
                        .name("Coca-Cola")
                        .price(1.50)
                        .stock(10)
                        .stockQuantity(14)
                        .build();

                Product pepsi = Product.builder()
                        .name("Pepsi")
                        .price(1.45)
                        .stock(8)
                        .stockQuantity(10)
                        .build();

                Product water = Product.builder()
                        .name("Mineral Water")
                        .price(1.00)
                        .stock(15)
                        .stockQuantity(18)
                        .build();

                Product chips = Product.builder()
                        .name("Potato Chips")
                        .price(2.00)
                        .stock(5)
                        .stockQuantity(12)
                        .build();

                Product chocolate = Product.builder()
                        .name("Chocolate Bar")
                        .price(1.75)
                        .stock(12)
                        .stockQuantity(13)
                        .build();

                List<Product> savedProducts = productRepository.saveAll(List.of(coke, pepsi, water, chips, chocolate));

                // Création d'une transaction d'exemple
                Transaction transaction1 = Transaction.builder()
                        .amountReceived(5.00)
                        .totalAmount(3.50)
                        .changeGiven(1.50)
                        .status(TransactionStatus.COMPLETED)
                        .timestamp(LocalDateTime.now().minusDays(2))
                        .build();

                TransactionItem item1 = TransactionItem.builder()
                        .product(savedProducts.get(0)) // Coke
                        .quantity(2)
                        .unitPrice(savedProducts.get(0).getPrice())
                        .transaction(transaction1)
                        .build();

                TransactionItem item2 = TransactionItem.builder()
                        .product(savedProducts.get(3)) // Chips
                        .quantity(1)
                        .unitPrice(savedProducts.get(3).getPrice())
                        .transaction(transaction1)
                        .build();

                transaction1.setItems(List.of(item1, item2));
                transactionRepository.save(transaction1);

                System.out.println("Initial data loaded successfully!");
            }
        };
    }
}
