package com.example.funpay;
import java.time.Instant;
// DTO
public record ApiError (
  int status,
  String error,
  String message,
  Instant timeStamp
){}