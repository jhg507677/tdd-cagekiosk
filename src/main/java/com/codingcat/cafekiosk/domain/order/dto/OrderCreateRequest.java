package com.codingcat.cafekiosk.domain.order.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {
  @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
  private List<String> productNumbers; // 여러개 주문 할 수있으니 복수형으로

  @Builder
  public OrderCreateRequest(List<String> productNumbers){
    this.productNumbers = productNumbers;
  }
}
