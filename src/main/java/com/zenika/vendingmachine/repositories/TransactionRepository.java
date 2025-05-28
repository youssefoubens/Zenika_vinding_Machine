package com.zenika.vendingmachine.repositories;

import com.zenika.vendingmachine.entities.Transaction;
import com.zenika.vendingmachine.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByStatus(TransactionStatus status);
    List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}