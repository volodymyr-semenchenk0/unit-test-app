package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BankAccountTest {

    @Test
    void testCreateBankAccountWithInitialBalance() {
        BankAccount account = new BankAccount(1000.0);
        assertEquals(1000.0, account.getBalance(), "Initial balance should be 1000.0");
    }
}
