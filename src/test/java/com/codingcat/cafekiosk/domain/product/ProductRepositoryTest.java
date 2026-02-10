package com.codingcat.cafekiosk.domain.product;

import static com.codingcat.cafekiosk.domain.product.ProductSellingStatus.HOLD;
import static com.codingcat.cafekiosk.domain.product.ProductSellingStatus.SELLING;
import static com.codingcat.cafekiosk.domain.product.ProductSellingStatus.STOP_SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/*
@SpringBootTest : 애플리케이션 전체 컨텍스트를 로딩하는 통합 테스트용 어노테이션
@DataJpaTest     : JPA 관련 빈만 로딩하여 Repository 계층을 빠르게 검증하는 테스트용 어노테이션
, 클렌징 안해도 자동으로 롤백함(트랜잭션)
@ActiveProfiles("test") : local이 실행될경우 data.sql이 같이 실행되므로 test로 환경 지정
*/
@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {
  @Autowired
  ProductRepository productRepository;

  @Test
  void findAllBySellingStatusIn() {
    // given
    Product product1 = Product.builder()
      .productNumber("001")
      .type(ProductType.HANDMADE)
      .sellingStatus(SELLING)
      .name("아메리카노")
      .price(4000)
      .build();
    Product product2 = Product.builder()
      .productNumber("002")
      .type(ProductType.HANDMADE)
      .sellingStatus(HOLD)
      .name("카페라떼")
      .price(4500)
      .build();
    Product product3 = Product.builder()
      .productNumber("003")
      .type(ProductType.HANDMADE)
      .sellingStatus(STOP_SELLING)
      .name("팥빙수")
      .price(7000)
      .build();
    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    List<Product> productList = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

    // 리스트 검증
    assertThat(productList)
      .hasSize(2)
      .extracting("productNumber", "name","sellingStatus")
      .containsExactlyInAnyOrder(
        tuple("001","아메리카노",SELLING),
        tuple("002","카페라떼",HOLD)
      );
  }

  @DisplayName("상품 코드 목록으로 상품을 조회한다")
  @Test
  void findAllByProductNumberIn(){
    // given
    Product product1 = Product.builder()
      .productNumber("001")
      .type(ProductType.HANDMADE)
      .sellingStatus(SELLING)
      .name("아메리카노")
      .price(4000)
      .build();
    Product product2 = Product.builder()
      .productNumber("002")
      .type(ProductType.HANDMADE)
      .sellingStatus(HOLD)
      .name("카페라떼")
      .price(4500)
      .build();
    Product product3 = Product.builder()
      .productNumber("003")
      .type(ProductType.HANDMADE)
      .sellingStatus(STOP_SELLING)
      .name("팥빙수")
      .price(7000)
      .build();
    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    List<Product> productList = productRepository.findAllByProductNumberIn(List.of("001","002"));


    // then
    assertThat(productList).hasSize(2);
    assertThat(productList)
      .extracting("name","price")
      .containsExactly(
        tuple("아메리카노", 4000),
        tuple("카페라떼", 4500)
      );
  }


  @DisplayName("================ 가장 마지막 상품 번호를 가져온다 ================ ")
  @Test
  void test(){
    // given
    Product product1 = createProduct("001", ProductType.HANDMADE, SELLING, "아메리카노", 4000);
    Product product2 = createProduct("002", ProductType.HANDMADE, HOLD, "카페라떼", 4500);
    Product product3 = createProduct("003", ProductType.HANDMADE, STOP_SELLING, "팥빙수", 7000);
    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    String result = productRepository.findLatestProductNumber();

    // then
    assertThat(result).isEqualTo("003");
  }

  @DisplayName("================ 가장 마지막 상품 번호를 가져올 때 상품이 하나도 없는 경우 null을 반환한다. ================ ")
  @Test
  void findLatestProductNumberWhenProductIsEmpty(){
    // when
    String result = productRepository.findLatestProductNumber();

    // then
    assertThat(result).isNull();
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