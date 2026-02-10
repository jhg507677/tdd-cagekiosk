package com.codingcat.cafekiosk.api.service.product;

import static com.codingcat.cafekiosk.domain.product.ProductSellingStatus.HOLD;
import static com.codingcat.cafekiosk.domain.product.ProductSellingStatus.SELLING;
import static com.codingcat.cafekiosk.domain.product.ProductSellingStatus.STOP_SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.codingcat.cafekiosk.api.service.product.dto.CreateProductRequest;
import com.codingcat.cafekiosk.api.service.product.dto.ProductResponse;
import com.codingcat.cafekiosk.domain.product.Product;
import com.codingcat.cafekiosk.domain.product.ProductRepository;
import com.codingcat.cafekiosk.domain.product.ProductSellingStatus;
import com.codingcat.cafekiosk.domain.product.ProductType;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {
  @Autowired private ProductService productService;
  @Autowired ProductRepository productRepository;

  @AfterEach
  void tearDown() {
    productRepository.deleteAllInBatch();
  }


  @DisplayName("================ 신규 상품을 등록한다. 상품 번호는 가장 최근 상품의 상품번호 + 1 이다. ================ ")
  @Test
  void createProduct(){
    // given

    // given
    Product product1 = createProduct("001", ProductType.HANDMADE, SELLING, "아메리카노", 4000);
    Product product2 = createProduct("002", ProductType.HANDMADE, HOLD, "카페라떼", 4500);
    Product product3 = createProduct("003", ProductType.HANDMADE, STOP_SELLING, "팥빙수", 7000);
    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    CreateProductRequest request = CreateProductRequest.builder()
      .type(ProductType.HANDMADE)
      .sellingStatus(SELLING)
      .name("카푸치노")
      .price(5000)
      .build();

    ProductResponse savedProduct = productService.createProduct(request);
    // then
    assertThat(product3.getId() + 1).isEqualTo(savedProduct.getId());
    assertThat(savedProduct)
      .extracting("productNumber", "type","sellingStatus","name","price")
      .contains("004", ProductType.HANDMADE, SELLING, "카푸치노", 5000);

    assertThat(productRepository.findAll()).hasSize(4);

  }

  @DisplayName("================ 신규 상품을 등록한다. 이전 상품이 없는 경우 등록번호는 001이다. ================ ")
  @Test
  void createFirstProduct(){
    CreateProductRequest request = CreateProductRequest.builder()
      .type(ProductType.HANDMADE)
      .sellingStatus(SELLING)
      .name("카푸치노")
      .price(5000)
      .build();

    ProductResponse savedProduct = productService.createProduct(request);
    assertThat(savedProduct)
      .extracting("productNumber", "type","sellingStatus","name","price")
      .contains("001", ProductType.HANDMADE, SELLING, "카푸치노", 5000);

    assertThat(productRepository.findAll()).hasSize(1);
  }

  public Product createProduct(
    String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name,
    int price
  ){
    return Product.builder()
      .productNumber(productNumber)
      .type(type)
      .sellingStatus(sellingStatus)
      .name(name)
      .price(price)
      .build();
  }
}