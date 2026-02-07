package com.codingcat.cafekiosk.domain.order;

import com.codingcat.cafekiosk.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
