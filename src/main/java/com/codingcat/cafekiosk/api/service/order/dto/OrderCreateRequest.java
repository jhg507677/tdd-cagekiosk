package com.codingcat.cafekiosk.api.service.order.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {
  private List<String> productNumbers; // 여러개 주문 할 수있으니 복수형으로

  @Builder
  public OrderCreateRequest(List<String> productNumbers){
    this.productNumbers = productNumbers;
  }
}
