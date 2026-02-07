package com.codingcat.cafekiosk.domain.order;

import com.codingcat.cafekiosk.domain.BaseEntity;
import com.codingcat.cafekiosk.domain.orderproduct.OrderProduct;
import com.codingcat.cafekiosk.domain.product.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  private int totalPrice;
  private LocalDateTime registeredDateTime;

  // 주문 하나에 상품은 여러개
  // 주문 테이블 삭제시 주문 아이템들 모두 삭제
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderProduct> orderProducts= new ArrayList<>();

  // Order 생성시 가격 계산
  // 각각의 단위 테스트 필요
  // registeredDateTime 같은 경우 단위 테스트를 위해 파라미터로 빼야함
  public Order(List<Product> products, LocalDateTime registeredDateTime){
    this.orderStatus = OrderStatus.INIT;
    this.totalPrice = calculateTotalPrice(products);
    this.registeredDateTime = registeredDateTime;
    this.orderProducts = products.stream()
      .map(product -> new OrderProduct(this, product))
      .collect(Collectors.toList());
  }

  public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
    return new Order(products, registeredDateTime);
  }

  private int calculateTotalPrice(List<Product> products) {
    return products.stream()
      .mapToInt(Product::getPrice)
      .sum();
  }
}
