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
    private String name;
    private double price;
    private int stock;
    private int stockQuantity;

    public boolean isPurchasable(BigDecimal currentBalance) {
        return stock > 0 && currentBalance.compareTo(BigDecimal.valueOf(price)) >= 0;
    }


}
