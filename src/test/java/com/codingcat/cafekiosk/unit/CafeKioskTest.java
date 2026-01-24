package com.codingcat.cafekiosk.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codingcat.cafekiosk.unit.beverage.Americano;
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
  void clear() throws IllegalAccessException {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();
    cafeKiosk.add(americano, 1);
    assertThat(cafeKiosk.getBeverages()).hasSize(1);
    cafeKiosk.clear();
    assertThat(cafeKiosk.getBeverages()).isEmpty();
  }

  @Test
  void calculateTotalPrice() {
  }

  @Test
  void createOrder() {
  }
}