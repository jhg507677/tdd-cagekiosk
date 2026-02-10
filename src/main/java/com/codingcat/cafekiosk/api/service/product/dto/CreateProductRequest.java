package com.codingcat.cafekiosk.api.service.product.dto;

import com.codingcat.cafekiosk.domain.product.Product;
import com.codingcat.cafekiosk.domain.product.ProductSellingStatus;
import com.codingcat.cafekiosk.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateProductRequest {
  private ProductType type;
  private ProductSellingStatus sellingStatus;
  private String name;
  private int price;

  @Builder
  public CreateProductRequest(ProductType type, ProductSellingStatus sellingStatus, String name,
    int price) {
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }

  public Product toEntity(String productNumber) {
    return Product.builder()
      .type(type)
      .productNumber(productNumber)
      .sellingStatus(sellingStatus)
      .name(name)
      .price(price)
      .build();
  }
}
