package com.zenika.vendingmachine.entities;

import com.zenika.vendingmachine.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionItem> items = new ArrayList<>();

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private double amountReceived;

    @Column(nullable = false)
    private double changeGiven;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @ElementCollection
    @CollectionTable(name = "product_change_breakdown", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "coin")
    @Column(name = "quantity")
    private Map<String, Integer> changeBreakdown = new HashMap<>();

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Calculates remaining balance.
     */
    public double getRemainingBalance() {
        return amountReceived - totalAmount;
    }

    /**
     * Calculates total number of items.
     */
    public int getTotalItemsInCart() {
        return items.stream().mapToInt(TransactionItem::getQuantity).sum();
    }

    /**
     * Determines if transaction can be completed (enough money received).
     */
    public boolean canComplete() {
        return amountReceived >= totalAmount && status == TransactionStatus.IN_PROGRESS;
    }


}
