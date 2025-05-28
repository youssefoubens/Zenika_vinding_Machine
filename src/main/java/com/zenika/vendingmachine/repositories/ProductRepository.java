package com.zenika.vendingmachine.repositories;

import com.zenika.vendingmachine.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStockGreaterThan(int stock);

    List<Product> findByQuantityGreaterThan(int i);
}