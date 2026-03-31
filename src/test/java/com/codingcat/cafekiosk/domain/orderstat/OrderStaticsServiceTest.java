package com.codingcat.cafekiosk.domain.orderstat;

import static com.codingcat.cafekiosk.domain.product.ProductSellingStatus.HOLD;
import static com.codingcat.cafekiosk.domain.product.ProductSellingStatus.SELLING;
import static com.codingcat.cafekiosk.domain.product.ProductSellingStatus.STOP_SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import com.codingcat.cafekiosk.domain.order.Order;
import com.codingcat.cafekiosk.domain.order.OrderItemRepository;
import com.codingcat.cafekiosk.domain.order.OrderRepository;
import com.codingcat.cafekiosk.domain.order.OrderStatus;
import com.codingcat.cafekiosk.domain.product.Product;
import com.codingcat.cafekiosk.domain.product.ProductRepository;
import com.codingcat.cafekiosk.domain.product.ProductSellingStatus;
import com.codingcat.cafekiosk.domain.product.ProductType;
import com.codingcat.cafekiosk.module.alert.mail.MailLog;
import com.codingcat.cafekiosk.module.alert.mail.MailLogRepository;
import com.codingcat.cafekiosk.module.alert.mail.MailSendClient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class OrderStaticsServiceTest {

  @Autowired private OrderStaticsService orderStaticsService;
  @Autowired private OrderItemRepository orderItemRepository;
  @Autowired private OrderRepository orderRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private MailLogRepository mailLogRepository;

  // 실제 메일 전송하지 않고 Mock 객체로 지정
  @MockBean private MailSendClient mailSendClient;

  @AfterEach
  void tearDown() {
    orderItemRepository.deleteAllInBatch();
    orderRepository.deleteAllInBatch();
    productRepository.deleteAllInBatch();
    mailLogRepository.deleteAllInBatch();
  }


  @DisplayName("================ 결제 완료 주문들의 하루 매출 통계 메일을 전송 ================ ")
  @Test
  void sendOrderStaticsMail() {
    // given
    // 주문 상품 상품 생성
    Product product1 = createProduct("001", ProductType.HANDMADE, SELLING, "아메리카노", 4000);
    Product product2 = createProduct("002", ProductType.HANDMADE, HOLD, "카페라떼", 4500);
    Product product3 = createProduct("003", ProductType.HANDMADE, STOP_SELLING, "팥빙수", 7000);
    productRepository.saveAll(List.of(product1, product2, product3));

    // 결제 완료 상태의 주문 저장
    LocalDateTime oneTime = LocalDateTime.of(2023, 3, 5, 0, 0);
    LocalDateTime twoTime = LocalDateTime.of(2023, 3, 4, 0, 0);
    LocalDateTime threeTime = LocalDateTime.of(2023, 3, 6, 0, 0);
    LocalDateTime fourTime = LocalDateTime.of(2023, 3, 5, 23, 59);
    Order order1 = createPaymentCompletedOrder(product1, product2, product3, oneTime);
    Order order2 = createPaymentCompletedOrder(product1, product2, product3, twoTime);
    Order order3 = createPaymentCompletedOrder(product1, product2, product3, threeTime);
    Order order4 = createPaymentCompletedOrder(product1, product2, product3, fourTime);

    // sendEmail의 파라미터로 String 4개가 들어갈경우 무조건 true로 넘겨주는 mock 객체
    // stubbing
    Mockito.when(mailSendClient.sendMail(
      any(String.class),any(String.class),any(String.class),any(String.class)
    )).thenReturn(true);

    // when
    boolean result = orderStaticsService.sendOrderStaticsMail(LocalDate.of(2023, 3, 5), "test@test.com");

    // then
    assertThat(result).isTrue();
    List<MailLog> mailLogs = mailLogRepository.findAll();
    assertThat(mailLogs)
      .hasSize(1)
      .extracting("content")
      .contains("총 매출 합계는 31000원입니다.")
      ;


  }

  private Order createPaymentCompletedOrder(
    Product product1, Product product2, Product product3,
    LocalDateTime localDateTime
  ) {
    Order order = Order.builder()
      .products(List.of(product1, product2, product3))
      .orderStatus(OrderStatus.PAYMENT_COMPLETED)
        .registeredDateTime(localDateTime)
        .build();
    return orderRepository.save(order);
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