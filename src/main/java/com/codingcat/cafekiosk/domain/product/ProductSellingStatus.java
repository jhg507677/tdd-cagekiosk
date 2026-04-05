package com.codingcat.cafekiosk.domain.product;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 판매 상태
@Getter
@RequiredArgsConstructor
public enum ProductSellingStatus {
  SELLING("판매중"),
  HOLD("판매 보류"),
  STOP_SELLING("판매 중지")
  ;

  private final String desc;

  public static List<ProductSellingStatus> forDisplay() {
    return List.of(SELLING, HOLD);
  }
}
