package com.codingcat.cafekiosk.domain.orderstat;

import com.codingcat.cafekiosk.domain.order.Order;
import com.codingcat.cafekiosk.domain.order.OrderRepository;
import com.codingcat.cafekiosk.domain.order.OrderStatus;
import com.codingcat.cafekiosk.module.alert.mail.MailService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderStaticsService {
  private final OrderRepository orderRepository;
  private final MailService mailService;

  // 해당 일자에 결제 완료된 주문들을 가져와서 총 매출 합계를 계산하고 메일 전송
  public boolean sendOrderStaticsMail(
    LocalDate orderData, String email
  ){
    List<Order> orders = orderRepository.findOrdersByDate(
      orderData.atStartOfDay(), orderData.plusDays(1).atStartOfDay(), OrderStatus.PAYMENT_COMPLETED
    );

    // 매출 합계
    int totalAmount = orders.stream().mapToInt(Order::getTotalPrice)
      .sum();

    // 메일 전송
    boolean result = mailService.sendMail("no-reply@test.com", email,
      String.format("매출 통계 %s", orderData),
      String.format("총 매출 합계는 %s원입니다.", totalAmount)
    );
    if(!result){
      throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
    }
    return true;
  }
}
