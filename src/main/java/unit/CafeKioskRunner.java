package unit;

import unit.beverage.Americano;
import unit.order.Order;

import java.time.LocalDateTime;

public class CafeKioskRunner {
  public static void main(String[] args) {
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano(), 1);
    cafeKiosk.add(new Latte(), 1);
    
    int totalPrice = cafeKiosk.calculateTotalPrice();
    System.out.printf("총 주문 가격 : %d", totalPrice);
    
    Order order = cafeKiosk.createOrder(LocalDateTime.now());
  }
}
