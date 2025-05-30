package com.backend.exception;

public class WalletBalanceException extends RuntimeException {
    public WalletBalanceException(String message) {
      super("BALANCE ERROR: " + message);
    }
}
