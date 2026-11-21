package com.example.funpay;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.dao.DataIntegrityViolationException;
import java.time.Instant;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;

import com.example.funpay.Exceptions.UserNotAuthenticatedException;
import com.example.funpay.Exceptions.UserNotFoundException;

@RestControllerAdvice 
public class GlobalExceptionHandler {
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiError> handleMissingBody(HttpMessageNotReadableException ex){
    ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", "Request body is empty", Instant.now());
    return ResponseEntity.badRequest().body(error);
  }
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleDbErrors(DataIntegrityViolationException ex){
    ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", ex.getMessage(), Instant.now());
    return ResponseEntity.badRequest().body(error);
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex){
    String cleanMessage = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
    ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", cleanMessage, Instant.now());
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiError> handleUserResponseEntity(UserNotFoundException e){
    String message = "user not found";
    ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), message, Instant.now());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND );
  }
  @ExceptionHandler(UserNotAuthenticatedException.class)
  public ResponseEntity<ApiError> handleUnauthenticatedUser(UserNotAuthenticatedException e){
    ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage(), Instant.now());
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED );
  }
}
