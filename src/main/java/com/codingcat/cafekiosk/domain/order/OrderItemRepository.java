package com.codingcat.cafekiosk.domain.order;

import com.codingcat.cafekiosk.domain.orderproduct.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
