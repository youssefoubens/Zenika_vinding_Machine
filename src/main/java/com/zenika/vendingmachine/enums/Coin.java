package com.zenika.vendingmachine.enums;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Enum representing valid coin denominations in MAD (Moroccan Dirham)
 */
public enum Coin {
    HALF_DIRHAM(new BigDecimal("0.5")),
    ONE_DIRHAM(new BigDecimal("1.0")),
    TWO_DIRHAM(new BigDecimal("2.0")),
    FIVE_DIRHAM(new BigDecimal("5.0")),
    TEN_DIRHAM(new BigDecimal("10.0"));

    private final BigDecimal value;

    Coin(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    /**
     * Validates if a given value corresponds to a valid coin
     */
    public static boolean isValidCoinValue(BigDecimal value) {
        return Arrays.stream(Coin.values())
                .anyMatch(coin -> coin.getValue().compareTo(value) == 0);
    }

    /**
     * Gets coin by value
     */
    public static Coin fromValue(BigDecimal value) {
        return Arrays.stream(Coin.values())
                .filter(coin -> coin.getValue().compareTo(value) == 0)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid coin value: " + value));
    }

    @Override
    public String toString() {
        return value + " MAD";
    }
}