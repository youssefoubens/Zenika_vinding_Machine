package com.zenika.vendingmachine.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock;

    /**
     * Determines if the product can be purchased with the current balance
     */
    public boolean isPurchasable(BigDecimal currentBalance) {
        return stock > 0 && currentBalance.compareTo(BigDecimal.valueOf(price)) >= 0;
    }


}
