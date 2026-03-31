package com.codingcat.cafekiosk.domain.order;

import com.codingcat.cafekiosk.domain.order.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("""
    SELECT o
    FROM Order AS o
    WHERE o.registeredDateTime >= :startDateTime
      AND o.registeredDateTime < :endDateTime
      AND o.orderStatus = :orderStatus
""")
  List<Order> findOrdersByDate(
    @Param("startDateTime") LocalDateTime startDateTime,
    @Param("endDateTime") LocalDateTime endDateTime,
    @Param("orderStatus") OrderStatus orderStatus
  );
}
