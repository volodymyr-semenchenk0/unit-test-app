package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BankAccount {
    private BigDecimal balance;

    public BankAccount(double initiationBalance) {
        if (initiationBalance <= 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.balance = BigDecimal.valueOf(initiationBalance);
    }

    public BigDecimal getBalance() {
        return this.balance.setScale(2,  RoundingMode.HALF_UP);
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance = this.balance.add(BigDecimal.valueOf(amount));
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > this.balance.doubleValue()) {
            throw new IllegalStateException("Insufficient funds.");
        }
        this.balance = this.balance.subtract(BigDecimal.valueOf(amount));
    }
}