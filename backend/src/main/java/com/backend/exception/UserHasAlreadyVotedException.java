package com.backend.exception;

public class UserHasAlreadyVotedException extends RuntimeException {
    public UserHasAlreadyVotedException(String message) {
        super("USER HAS ALREADY VOTED THIS POST" +message);
    }
}
