package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {
    private BankAccount account;

    @BeforeEach
    void initializeBalance() {
        this.account = new BankAccount(10000.00);
    }

    @Test
        // Tests that the initial balance is set correctly when a bank account is created.
    void testCreateBankAccountWithInitialBalance() {
        assertEquals(0, this.account.getBalance().compareTo(new BigDecimal("10000.00")),
                "Initial balance should be 10000.00");
    }

    @Test
        // Tests that creating a bank account with a negative initial balance throws an IllegalArgumentException.
    void testCreateBankAccountWithNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(-100.00),
                "Expected IllegalArgumentException when attempting to create a bank account with a negative initial balance");
    }

    @Test
        // Tests that the balance is correctly rounded to two decimal places when retrieved.
    void testBalancePrecisionOnGetBalance() {
        BankAccount newAccount = new BankAccount(1000.23123);
        BigDecimal expectedBalance = new BigDecimal("1000.23");

        assertEquals(0, expectedBalance.compareTo(newAccount.getBalance()),
                "Balance should be correctly rounded to 10000.23 when retrieved");
    }

    @ParameterizedTest
    @ValueSource(doubles = {200, 120.73, 0.32, 122})
        // Tests that a valid withdrawal amount correctly reduces the balance.
    void testWithdrawValidAmount(double amount) {
        this.account.withdraw(amount);
        BigDecimal expectedBalanceAfterWithdraw = new BigDecimal("10000.00").subtract(BigDecimal.valueOf(amount));

        assertEquals(0, expectedBalanceAfterWithdraw.compareTo(this.account.getBalance()),
                "Balance should be " + expectedBalanceAfterWithdraw + " after withdrawal");
    }

    @Test
        // Tests that attempting to withdraw a negative amount throws an IllegalArgumentException.
    void testWithdrawInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> this.account.withdraw(-200.00),
                "Expected IllegalArgumentException when withdrawing a negative amount");
    }

    @Test
        // Tests that attempting to withdraw more than the available balance throws an IllegalStateException.
    void testWithdrawMoreThenBalance() {
        assertThrows(IllegalStateException.class, () -> this.account.withdraw(200000.90),
                "Expected IllegalStateException when withdrawing more than available balance");
    }

    @Test
        // Tests that withdrawing the entire balance results in a balance of zero.
    void testWithdrawAllBalance() {
        this.account.withdraw(10000.00);
        assertEquals(0, this.account.getBalance().compareTo(BigDecimal.ZERO),
                "Balance should be ZERO after withdrawal");
    }

    @Test
        // Tests that depositing a valid amount correctly increases the balance.
    void testDepositValidAmount() {
        BigDecimal initialBalance = this.account.getBalance();
        double depositAmount = 200.22;

        this.account.deposit(depositAmount);
        BigDecimal expectedBalanceAfterDeposit = initialBalance.add(BigDecimal.valueOf(depositAmount));

        assertEquals(0, expectedBalanceAfterDeposit.compareTo(this.account.getBalance()),
                "Balance should be " + expectedBalanceAfterDeposit + " after deposit");
    }

    @Test
        // Tests that attempting to deposit a negative amount throws an IllegalArgumentException.
    void testDepositInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> this.account.deposit(-200.22),
                "Expected IllegalArgumentException when attempting to deposit a negative amount");
    }

    @Test
        // Tests that if no transactions are made, the balance remains unchanged.
    void testNoTransactions() {
        assertEquals(0, this.account.getBalance().compareTo(new BigDecimal("10000.00")),
                "Balance should be unchanged if no transactions");
    }

    // Tests that the balance is correctly updated after multiple deposit and withdrawal transactions.
    @Test
    void testBalanceAfterMultipleTransactions() {
        BigDecimal initialBalance = this.account.getBalance();

        this.account.deposit(500.00); // +500.00
        this.account.withdraw(200.00); // -200.00
        this.account.deposit(1500.75); // +1500.75
        this.account.withdraw(300.50); // -300.50
        this.account.deposit(100.25); // +100.25

        BigDecimal expectedBalance = initialBalance
                .add(BigDecimal.valueOf(500.00))   // Add deposit of 500.00
                .subtract(BigDecimal.valueOf(200.00)) // Subtract withdrawal of 200.00
                .add(BigDecimal.valueOf(1500.75)) // Add deposit of 1500.75
                .subtract(BigDecimal.valueOf(300.50)) // Subtract withdrawal of 300.50
                .add(BigDecimal.valueOf(100.25));  // Add deposit of 100.25

        // The expected final balance is 10000.00 + 500.00 - 200.00 + 1500.75 - 300.50 + 100.25 = 11600.50
        assertEquals(0, expectedBalance.compareTo(this.account.getBalance()),
                "Balance should be correctly updated after multiple transactions");
    }
}