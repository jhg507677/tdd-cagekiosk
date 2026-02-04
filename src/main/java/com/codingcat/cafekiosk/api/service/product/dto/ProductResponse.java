package com.codingcat.cafekiosk.api.service.product.dto;

import com.codingcat.cafekiosk.domain.product.Product;
import com.codingcat.cafekiosk.domain.product.ProductSellingStatus;
import com.codingcat.cafekiosk.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {
  private Long id;
  private String productNumber;
  private ProductType type;
  private ProductSellingStatus sellingStatus;
  private String name;
  private int price;

  public static ProductResponse of(Product item) {
    return ProductResponse.builder()
      .id(item.getId())
      .productNumber(item.getProductNumber())
      .type(item.getType())
      .sellingStatus(item.getSellingStatus())
      .name(item.getName())
      .price(item.getPrice())
      .build();
  }

  @Builder
  private ProductResponse(Long id, String productNumber, ProductType type,
    ProductSellingStatus sellingStatus, String name, int price) {
    this.id = id;
    this.productNumber = productNumber;
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }
}
