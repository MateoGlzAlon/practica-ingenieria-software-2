package com.backend.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
      super("USER HAS NOT BEEN FOUND: " + message);
    }
}
