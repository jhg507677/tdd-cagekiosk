package com.codingcat.cafekiosk.controller;

import com.codingcat.cafekiosk.domain.order.OrderService;
import com.codingcat.cafekiosk.domain.order.dto.OrderCreateRequest;
import com.codingcat.cafekiosk.domain.order.dto.OrderResponse;
import com.codingcat.cafekiosk.module.model.ApiResponseVo;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {
  private final OrderService orderService;

  @PostMapping("/api/v1/orders")
  public ApiResponseVo<OrderResponse> createOrder(
    @Valid @RequestBody OrderCreateRequest request
  ){
    return ApiResponseVo.ok(orderService.createOrder(request, LocalDateTime.now()));
  }
}
