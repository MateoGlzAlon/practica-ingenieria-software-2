package com.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Responds with 500 status
  public ResponseEntity<String> handleGenericException(Exception ex) {
    return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST) // Responds with 400 status
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserHasAlreadyVotedException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // Responds with 422
  public ResponseEntity<String> handleUserHasAlreadyVotedException(UserHasAlreadyVotedException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(WalletBalanceException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // Responds with 422
  public ResponseEntity<String> handleWalletBalanceException(UserHasAlreadyVotedException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Responds with 500
  public ResponseEntity<String> handleUserNotFoundException(UserHasAlreadyVotedException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }



}
