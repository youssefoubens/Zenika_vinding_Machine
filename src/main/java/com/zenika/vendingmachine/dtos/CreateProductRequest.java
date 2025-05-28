// src/main/java/com/zenika/vendingmachine/dtos/CreateProductRequest.java

package com.zenika.vendingmachine.dtos;

import lombok.Data;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductRequest {
    private String name;
    private double price;
}
