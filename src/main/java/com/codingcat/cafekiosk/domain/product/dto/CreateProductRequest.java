package com.codingcat.cafekiosk.domain.product.dto;

import com.codingcat.cafekiosk.domain.product.Product;
import com.codingcat.cafekiosk.domain.product.ProductSellingStatus;
import com.codingcat.cafekiosk.domain.product.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateProductRequest {
  @NotNull(message = "상품 타입은 필수입니다.")
  private ProductType type;

  @NotNull(message = "상품 판매상태는 필수입니다.")
  private ProductSellingStatus sellingStatus;

  @NotBlank(message = "상품 이름은 필수입니다.")
  private String name;

  @Positive(message = "상품 가격은 양수여야합니다.")
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
