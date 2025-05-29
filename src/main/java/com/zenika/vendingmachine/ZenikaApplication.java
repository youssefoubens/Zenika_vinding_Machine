package com.zenika.vendingmachine;

import com.zenika.vendingmachine.entities.Product;
import com.zenika.vendingmachine.entities.Transaction;
import com.zenika.vendingmachine.entities.TransactionItem;
import com.zenika.vendingmachine.enums.TransactionStatus;
import com.zenika.vendingmachine.repositories.ProductRepository;
import com.zenika.vendingmachine.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ZenikaApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ZenikaApplication.class, args);
    }


}
