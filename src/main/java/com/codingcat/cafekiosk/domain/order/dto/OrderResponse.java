package com.codingcat.cafekiosk.domain.order.dto;

import com.codingcat.cafekiosk.domain.product.dto.ProductResponse;
import com.codingcat.cafekiosk.domain.order.Order;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponse {
  private Long id;
  private int totalPrice;
  private LocalDateTime registeredDateTime;
  private List<ProductResponse> products;

  @Builder
  public OrderResponse(Long id, int totalPrice, LocalDateTime registeredDateTime,
    List<ProductResponse> products) {
    this.id = id;
    this.totalPrice = totalPrice;
    this.registeredDateTime = registeredDateTime;
    this.products = products;
  }

  public static OrderResponse toOrder(Order order) {
    return OrderResponse.builder()
      .id(order.getId())
      .totalPrice(order.getTotalPrice())
      .registeredDateTime(order.getRegisteredDateTime())

      .products(order.getOrderItems().stream()
        .map(product -> ProductResponse.of(product.getProduct()))
        .collect(Collectors.toList())
      )
    .build();
  }
}
