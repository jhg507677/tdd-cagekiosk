package com.codingcat.cafekiosk.api.service.order;

import static com.codingcat.cafekiosk.domain.product.ProductSellingStatus.SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import com.codingcat.cafekiosk.api.service.order.dto.OrderCreateRequest;
import com.codingcat.cafekiosk.api.service.order.dto.OrderResponse;
import com.codingcat.cafekiosk.domain.order.OrderProductRepository;
import com.codingcat.cafekiosk.domain.order.OrderRepository;
import com.codingcat.cafekiosk.domain.product.Product;
import com.codingcat.cafekiosk.domain.product.ProductRepository;
import com.codingcat.cafekiosk.domain.product.ProductType;
import com.codingcat.cafekiosk.domain.stock.Stock;
import com.codingcat.cafekiosk.domain.stock.StockRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {
  @Autowired OrderService orderService;
  @Autowired ProductRepository productRepository;
  @Autowired OrderRepository orderRepository;
  @Autowired OrderProductRepository orderProductRepository;
  @Autowired StockRepository stockRepository;

  // 하나의 테스트는 다른 테스트에 영향 받지 않게
  @AfterEach
  void tearDown() {
    orderProductRepository.deleteAllInBatch();
    productRepository.deleteAllInBatch();
    orderRepository.deleteAllInBatch();
    stockRepository.deleteAllInBatch();
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

  @DisplayName("================ 같은 상품을 2개이상 주문 할 수 있다. ================ ")
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

  @DisplayName("주문번호 리스트를 받아 주문을 생성시 재고 차감을 한다.")
  @Test
  void createOrderWithStock() {
    // given
    // 상품 및 재고 생성
    Product product1 = createProduct(ProductType.BOTTLE, "001", 1000);
    Product product2 = createProduct(ProductType.BAKERY, "002", 3000);
    Product product3 = createProduct(ProductType.HANDMADE, "003", 5000);
    productRepository.saveAll(List.of(product1, product2, product3));

    Stock stock1 = Stock.create("001", 2);
    Stock stock2 = Stock.create("002", 2);
    stockRepository.saveAll(List.of(stock1, stock2));

    OrderCreateRequest request = OrderCreateRequest.builder().productNumbers(List.of("001","001","002","003")).build();
    // when
    LocalDateTime registeredTime = LocalDateTime.now();
    OrderResponse orderResponse = orderService.createOrder(request, registeredTime);

    // then
    // contains는 순서 무시
    // containsExactly 순서 일치해야함
    assertThat(orderResponse.getId()).isNotNull();
    assertThat(orderResponse)
      .extracting("registeredDateTime","totalPrice")
      .contains(registeredTime, 10000);

    assertThat(orderResponse.getProducts()).hasSize(4)
      .extracting("productNumber","price")
      .containsExactly(
        tuple("001", 1000),
        tuple("001", 1000),
        tuple("002", 3000),
        tuple("003", 5000)
      );

    List<Stock> stocks = stockRepository.findAll();
    assertThat(stocks).hasSize(2)
      .extracting("productNumber","quantity")
      .containsExactly(
        tuple("001", 0),
        tuple("002", 1)
      );
  }


  @DisplayName("재고가 부족한 상품으로 주문을 생성할려는 경우 예외 발생")
  @Test
  void createOrderWithNoStock() {
    // given
    // 상품 및 재고 생성
    Product product1 = createProduct(ProductType.BOTTLE, "001", 1000);
    Product product2 = createProduct(ProductType.BAKERY, "002", 3000);
    Product product3 = createProduct(ProductType.HANDMADE, "003", 5000);
    productRepository.saveAll(List.of(product1, product2, product3));

    OrderCreateRequest request = OrderCreateRequest.builder().productNumbers(List.of("001","001","002","003")).build();
    // when
    LocalDateTime registeredTime = LocalDateTime.now();
    assertThatThrownBy(() -> orderService.createOrder(request, registeredTime))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("재고가 부족한 상품이 있습니다.");
  }
}