package com.codingcat.cafekiosk.domain.stock;

import com.codingcat.cafekiosk.domain.BaseEntity;
import jakarta.persistence.Entity;
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
public class Stock extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String productNumber;

  private int quantity;

  @Builder
  public Stock(String productNumber, int quantity) {
    this.productNumber = productNumber;
    this.quantity = quantity;
  }

  /**
   * 재고 생성
   * @param productNumber
   * @param quantity
   */
  public static Stock create(String productNumber, int quantity) {
    return Stock.builder()
      .productNumber(productNumber)
      .quantity(quantity)
      .build();
  }

  /*  창고의 재고와 요청시 재고를 비교해서 창고 재고가 더 적다면 return false  */
  public boolean isQuantityLessThanRequestQuantity(int requestQuantity) {
    return this.quantity < requestQuantity;
  }

  /*재고 차감*/
  // 서비스 로직과는 별개로 보장이 필요함
  // 커밋 시점에 실제 DB 차감을 함
  public void deductQuantity(int requestQuantity) {
    if(isQuantityLessThanRequestQuantity(requestQuantity)){
      throw new IllegalArgumentException("차감할 재고 수량이 없습니다.");
    }
    this.quantity -= requestQuantity;
  }
}