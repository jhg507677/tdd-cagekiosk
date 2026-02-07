package com.codingcat.cafekiosk.domain.product;

import com.codingcat.cafekiosk.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

  /*
    @GeneratedValue : PK 값을 자동 생성하도록 설정, DB에 ID 생성 책임을 위임
    GenerationType.IDENTITY : 데이터베이스의 AUTO_INCREMENT 전략 사용
  */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 상품 번호
  private String productNumber;

  // Enum 값을 DB에 “문자열”로 저장해 줌 -> HANDMADE
  @Enumerated(EnumType.STRING)
  private ProductType type;

  @Enumerated(EnumType.STRING)
  private ProductSellingStatus sellingStatus;


  private String name;
  private int price;

  @Builder
  public Product(
    String productNumber, ProductType type, ProductSellingStatus sellingStatus,
    String name, int price
  ) {
    this.productNumber = productNumber;
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }
}
