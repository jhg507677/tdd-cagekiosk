package com.codingcat.cafekiosk.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codingcat.cafekiosk.unit.beverage.Americano;
import com.codingcat.cafekiosk.unit.beverage.Latte;
import com.codingcat.cafekiosk.unit.order.Order;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CafeKioskTest {

  @Test
  void getBeverages() {
  }

  @Test
  void add_manual_test(){
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano(), 1);
    System.out.println(">>>> 담긴 음료 수 : "+cafeKiosk.getBeverages().size());
    System.out.println(">>>> 담긴 음료 : "+cafeKiosk.getBeverages().get(0).getName());
  }

  @DisplayName("음료 1개를 추가하면 주문 목록에 잠긴다")
  @Test
  void add(){
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano(), 1);
    assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
    assertThat(cafeKiosk.getBeverages()).hasSize(1);
    assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @Test
  void addSeveralBeverages(){
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano(), 2);
    assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @Test
  void addZeroBeverages(){
    CafeKiosk cafeKiosk = new CafeKiosk();
    assertThatThrownBy(()-> cafeKiosk.add(new Americano(), 0))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("음료는 1 잔 이상 주문하실 수 있습니다.")
    ;

  }

  @Test
  void remove(){
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();
    cafeKiosk.add(americano, 1);
    assertThat(cafeKiosk.getBeverages()).hasSize(1);
    cafeKiosk.remove(americano);
    assertThat(cafeKiosk.getBeverages()).isEmpty();

  }

  @Test
  void clear() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();
    cafeKiosk.add(americano, 1);
    assertThat(cafeKiosk.getBeverages()).hasSize(1);
    cafeKiosk.clear();
    assertThat(cafeKiosk.getBeverages()).isEmpty();
  }

  @Test
  void calculateTotalPrice() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();
    Latte latte = new Latte();
    cafeKiosk.add(americano, 1);
    cafeKiosk.add(latte, 1);

    int totalPrice = cafeKiosk.calculateTotalPrice();
    assertThat(totalPrice).isEqualTo(8500);
  }

  @Test
  void createOrder() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();
    cafeKiosk.add(americano, 1);
    Order order = cafeKiosk.createOrder();
    assertThat(order.getBeverageList()).hasSize(1);
  }

  @Test
  void createOrderWithCurrentTime() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();
    cafeKiosk.add(americano, 1);
    Order order = cafeKiosk.createOrder(LocalDateTime.of(2023,1, 17, 10, 0));
    assertThat(order.getBeverageList()).hasSize(1);
  }

  @Test
  void createOrderOutSideOpenType() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();
    cafeKiosk.add(americano, 1);
    assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023,1, 17, 9, 0)))
      .isInstanceOf(IllegalArgumentException.class).hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
  }

  @DisplayName("")
  @Test
  void test(){
    // given


    // when


    // then


  }
}