package com.codingcat.cafekiosk.domain.order;

import static com.codingcat.cafekiosk.domain.product.ProductSellingStatus.SELLING;
import static org.assertj.core.api.Assertions.assertThat;

import com.codingcat.cafekiosk.domain.product.Product;
import com.codingcat.cafekiosk.domain.product.ProductType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {
  @DisplayName("주문 생성시 상품 리스트에서 주문의 총 금액을 계산한다.")
  @Test
  void calculateTotalPrice(){
    // given
    List<Product> productList = List.of(
      createProduct("001", 1000),
      createProduct("002", 2000)
    );

    // when
    Order order = Order.create(productList, LocalDateTime.now());

    // then
    assertThat(order.getTotalPrice()).isEqualTo(3000);
  }

  @DisplayName("주문 생성시 주문의 상태는 INIT 이다.")
  @Test
  void checkOrderStatus(){
    // given
    List<Product> productList = List.of(
      createProduct("001", 1000),
      createProduct("002", 2000)
    );

    // when
    Order order = Order.create(productList, LocalDateTime.now());

    // then
    assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
  }

  @DisplayName("주문 생성시 등록 시간을 기록한다.")
  @Test
  void registeredDateTime(){
    LocalDateTime registeredDateTime = LocalDateTime.now();

    // given
    List<Product> productList = List.of(
      createProduct("001", 1000),
      createProduct("002", 2000)
    );

    // when
    Order order = Order.create(productList, registeredDateTime);

    // then
    assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
  }

  private Product createProduct(String productNumber, int price){
    Product product1 = Product.builder()
      .productNumber(productNumber)
      .type(ProductType.HANDMADE)
      .sellingStatus(SELLING)
      .name("메뉴 이름")
      .price(price)
      .build();
    return product1;
  }
}