package com.codingcat.cafekiosk.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

  @DisplayName("================ 요청한 상품 수량이 창고의 재고 수량보다 많다면 false (주문 가능한 상품인지 확인) =============== ")
  @Test
  void isQuantityLessThan(){
    // given
    Stock stock = Stock.create("001", 1);
    int requestQuantity = 2;

    // when
    boolean result = stock.isQuantityLessThanRequestQuantity(requestQuantity);

    // then
    assertThat(result).isTrue();

  }

  @DisplayName("================ 재고 수량 감소하기 ================ ")
  @Test
  void deductQuantity(){
    // given
    Stock stock = Stock.create("001", 2);
    int requestQuantity = 1;

    // when
    stock.deductQuantity(requestQuantity);

    // then
    assertThat(stock.getQuantity()).isEqualTo(1);
  }

  @DisplayName("================ 재고보다 많은 수의 수량으로 주문하는 경우 예외 발생 ================ ")
  @Test
  void deductQuantityFalse1(){
    // given
    Stock stock = Stock.create("001", 2);
    int requestQuantity = 4;

    assertThatThrownBy(() -> stock.deductQuantity(requestQuantity))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("차감할 재고 수량이 없습니다.");
  }
}