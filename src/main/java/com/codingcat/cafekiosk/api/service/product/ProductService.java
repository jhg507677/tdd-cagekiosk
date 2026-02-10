package com.codingcat.cafekiosk.api.service.product;

import com.codingcat.cafekiosk.api.service.product.dto.CreateProductRequest;
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

  // 상품 생성
  public ProductResponse createProduct(CreateProductRequest request) {
    // productNumber 생성
    // DB에서 마지막 저장된 product의 상품 번호를 읽어와서 + 1

    Product product = request.toEntity(createNextProductNumber());
    Product savedProduct = productRepository.save(product);
    return ProductResponse.of(savedProduct);
  }

  private String createNextProductNumber(){
    String latestProductNumber = productRepository.findLatestProductNumber();
    if(latestProductNumber == null) return "001";
    int latestProductNumberInt = Integer.valueOf(latestProductNumber);
    int nextProductNumberInt = latestProductNumberInt + 1;
    return String.format("%03d", nextProductNumberInt);
  }
}
