package com.backend.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
      super("USER HAS NOT BEEN FOUND: " + message);
    }
}
