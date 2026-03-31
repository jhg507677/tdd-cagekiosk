package com.codingcat.cafekiosk.domain.orderproduct;

import com.codingcat.cafekiosk.domain.BaseEntity;
import com.codingcat.cafekiosk.domain.order.Order;
import com.codingcat.cafekiosk.domain.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  public OrderItem(Order order, Product product) {
    this.order = order;
    this.product = product;
  }
}
