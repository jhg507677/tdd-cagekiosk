package com.codingcat.cafekiosk.module.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ApiResponseVo<T> {
  private HttpStatus status;
  private String message;
  private T data;

  public static <T> ApiResponseVo<T> of(HttpStatus status, String message, T data) {
    return ApiResponseVo.<T>builder()
      .status(status)
      .message(message)
      .data(data)
      .build();
  }

  public static <T> ApiResponseVo<T> ok(T data) {
    return ApiResponseVo.<T>builder()
      .status(HttpStatus.OK)
      .message("success")
      .data(data)
      .build();
  }
}
