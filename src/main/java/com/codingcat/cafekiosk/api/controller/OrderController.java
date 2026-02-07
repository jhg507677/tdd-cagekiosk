package com.codingcat.cafekiosk.api.controller;

import com.codingcat.cafekiosk.api.service.order.OrderService;
import com.codingcat.cafekiosk.api.service.order.dto.OrderCreateRequest;
import com.codingcat.cafekiosk.api.service.order.dto.OrderResponse;
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
  public OrderResponse createOrder(
    @RequestBody OrderCreateRequest request
  ){
    return orderService.createOrder(request, LocalDateTime.now());
  }
}
