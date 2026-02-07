package com.codingcat.cafekiosk.domain.order;

import com.codingcat.cafekiosk.domain.orderproduct.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
