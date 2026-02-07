package com.codingcat.cafekiosk.api.service.order;

import static com.codingcat.cafekiosk.domain.product.ProductSellingStatus.SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.codingcat.cafekiosk.api.service.order.dto.OrderCreateRequest;
import com.codingcat.cafekiosk.api.service.order.dto.OrderResponse;
import com.codingcat.cafekiosk.domain.order.OrderProductRepository;
import com.codingcat.cafekiosk.domain.order.OrderRepository;
import com.codingcat.cafekiosk.domain.product.Product;
import com.codingcat.cafekiosk.domain.product.ProductRepository;
import com.codingcat.cafekiosk.domain.product.ProductType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {
  @Autowired OrderService orderService;
  @Autowired ProductRepository productRepository;
  @Autowired OrderRepository orderRepository;
  @Autowired OrderProductRepository orderProductRepository;

  // 하나의 테스트는 다른 테스트에 영향 받지 않게
  @AfterEach
  void tearDown() {
    orderProductRepository.deleteAllInBatch();
    productRepository.deleteAllInBatch();
    orderRepository.deleteAllInBatch();
  }

  @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
  @Test
  void createOrder() {
    // given
    Product product1 = createProduct(ProductType.HANDMADE, "001", 1000);
    Product product2 = createProduct(ProductType.HANDMADE, "002", 3000);
    Product product3 = createProduct(ProductType.HANDMADE, "003", 5000);
    productRepository.saveAll(List.of(product1, product2, product3));

    OrderCreateRequest request = OrderCreateRequest.builder().productNumbers(List.of("001","002")).build();
    // when
    LocalDateTime registeredTime = LocalDateTime.now();
    OrderResponse orderResponse = orderService.createOrder(request, registeredTime);

    // then
    // contains는 순서 무시
    // containsExactly 순서 일치해야함
    assertThat(orderResponse.getId()).isNotNull();
    assertThat(orderResponse)
      .extracting("registeredDateTime","totalPrice")
      .contains(registeredTime, 4000);

    assertThat(orderResponse.getProducts()).hasSize(2)
      .extracting("productNumber","price")
      .containsExactly(
        tuple("001", 1000),
        tuple("002", 3000)
      );
  }

  @DisplayName("같은 상품을 2개이상 주문 할 수 있다.")
  @Test
  void createOrderWithDuplicateProductNumbers(){
    // given
    Product product1 = createProduct(ProductType.HANDMADE, "001", 1000);
    productRepository.saveAll(List.of(product1, product1));

    OrderCreateRequest request = OrderCreateRequest.builder().productNumbers(List.of("001","001")).build();
    // when
    LocalDateTime registeredTime = LocalDateTime.now();
    OrderResponse orderResponse = orderService.createOrder(request, registeredTime);

    // then
    // contains는 순서 무시
    // containsExactly 순서 일치해야함
    assertThat(orderResponse.getId()).isNotNull();
    assertThat(orderResponse)
      .extracting("registeredDateTime","totalPrice")

      .contains(registeredTime, 2000);

    assertThat(orderResponse.getProducts()).hasSize(2)
      .extracting("productNumber","price")
      .containsExactly(
        tuple("001", 1000),
        tuple("001", 1000)
      );
  }

  private Product createProduct(ProductType type, String productNumber, int price){
    Product product1 = Product.builder()
      .productNumber(productNumber)
      .type(type)
      .sellingStatus(SELLING)
      .name("메뉴 이름")
      .price(price)
      .build();
    return product1;
  }
}