package unit.beverage;

import com.codingcat.cafekiosk.unit.order.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import unit.CafeKiosk;
import unit.Latte;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafeKioskTest {
  @DisplayName("")
  @Test
  void add_manual(){
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano(), 1);
    System.out.printf(">>> 담긴 음료 수: %d \n",cafeKiosk.getBeverageList().size());
    System.out.printf(">>> 담긴 음료: %s",cafeKiosk.getBeverageList().get(0).getName());
  }
  
  @DisplayName("")
  @Test
  void add(){
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano(), 1);
    assertThat(cafeKiosk.getBeverageList()).hasSize(1);
    assertThat(cafeKiosk.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
  }
  
  
  @DisplayName("음료수를 여러개 주문 할 수 있다.")
  @Test
  void addSeveralBeverages(){
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano  = new Americano();
    
    cafeKiosk.add(americano, 2);
    
    assertThat(cafeKiosk.getBeverageList()).hasSize(2);
    assertThat(cafeKiosk.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
    assertThat(cafeKiosk.getBeverageList().get(1).getName()).isEqualTo("아메리카노");
  }
  
  @DisplayName("")
  @Test
  void calculateTotalPrice(){
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano  = new Americano();
    
    cafeKiosk.add(americano, 2);
    
    assertThat(cafeKiosk.calculateTotalPrice()).isEqualTo(8000);
  }
  
  @DisplayName("음료수를 주문할 경우 0개나 음수갯수는 불가능한다.")
  @Test
  void addSeveralBeveragesFail(){
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano  = new Americano();
    
    assertThatThrownBy(()-> cafeKiosk.add(americano, 0)).isInstanceOf(IllegalArgumentException.class).hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
    assertThatThrownBy(()-> cafeKiosk.add(americano, -1)).isInstanceOf(IllegalArgumentException.class).hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
  }
  
  @Test
  void createOrderWithCurrentTime() {
    com.codingcat.cafekiosk.unit.CafeKiosk cafeKiosk = new com.codingcat.cafekiosk.unit.CafeKiosk();
    com.codingcat.cafekiosk.unit.beverage.Americano americano = new com.codingcat.cafekiosk.unit.beverage.Americano();
    cafeKiosk.add(americano, 1);
    Order order = cafeKiosk.createOrder(LocalDateTime.of(2023,1, 17, 10, 0));
    assertThat(order.getBeverageList()).hasSize(1);
  }
  
  @DisplayName("주문 시간이 아닐 경우 테스트는 실패한다.")
  @Test
  void createOrderOutSideOpenType() {
    com.codingcat.cafekiosk.unit.CafeKiosk cafeKiosk = new com.codingcat.cafekiosk.unit.CafeKiosk();
    com.codingcat.cafekiosk.unit.beverage.Americano americano = new com.codingcat.cafekiosk.unit.beverage.Americano();
    cafeKiosk.add(americano, 1);
    assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023,1, 17, 9, 0)))
      .isInstanceOf(IllegalArgumentException.class).hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
  }
  
  @DisplayName("")
  @Test
  void remove(){
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano  = new Americano();
    cafeKiosk.add(americano, 1);
    assertThat(cafeKiosk.getBeverageList()).hasSize(1);
    
    cafeKiosk.remove(americano);
    assertThat(cafeKiosk.getBeverageList()).isEmpty();
  }
  
  @DisplayName("")
  @Test
  void clear(){
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano  = new Americano();
    cafeKiosk.add(americano, 1);
    assertThat(cafeKiosk.getBeverageList()).hasSize(1);
    
    Latte latte  = new Latte();
    cafeKiosk.add(latte, 1);
    assertThat(cafeKiosk.getBeverageList()).hasSize(2);
    
    cafeKiosk.clear();
    assertThat(cafeKiosk.getBeverageList()).isEmpty();
  }
}