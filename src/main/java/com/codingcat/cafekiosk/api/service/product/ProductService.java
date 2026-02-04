package com.codingcat.cafekiosk.api.service.product;

import com.codingcat.cafekiosk.api.service.product.dto.ProductResponse;
import com.codingcat.cafekiosk.domain.product.Product;
import com.codingcat.cafekiosk.domain.product.ProductRepository;
import com.codingcat.cafekiosk.domain.product.ProductSellingStatus;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ProductService {
  private final ProductRepository productRepository;

  // 상품 조회
  public List<ProductResponse> getSellingProducts(){
    List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());
    return products.stream()
      .map(ProductResponse::of)
      .collect(Collectors.toList());
  }
}
