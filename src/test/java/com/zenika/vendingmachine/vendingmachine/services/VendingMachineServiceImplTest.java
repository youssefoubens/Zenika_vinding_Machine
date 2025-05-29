package com.zenika.vendingmachine.vendingmachine.services;

import static org.junit.jupiter.api.Assertions.*;



import com.zenika.vendingmachine.services.VendingMachineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;

public class VendingMachineServiceImplTest {

    @InjectMocks
    private VendingMachineServiceImpl vendingMachineService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertCoin_validCoin_shouldAddAmount() {
        vendingMachineService.insertCoin(1.0);
        assertEquals(1.0, vendingMachineService.getCurrentBalance());
    }

    @Test
    void testInsertCoin_notvalidCoin_shouldAddAmount() {
        double Coin = 15.0;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            vendingMachineService.insertCoin(Coin);
        });

        assertTrue(exception.getMessage().contains("Invalid coin value"));
    }
    @Test
    void testInsertCoin_negativeAmount_shouldThrowException() {
        double negativeCoin = -1.0;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            vendingMachineService.insertCoin(negativeCoin);
        });

        assertTrue(exception.getMessage().contains("Amount must be positive"));
    }


    @Test
    void testInsertCoin_invalidCoin_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> vendingMachineService.insertCoin(0.03));
        assertTrue(exception.getMessage().contains("Invalid coin value"));
    }
}
