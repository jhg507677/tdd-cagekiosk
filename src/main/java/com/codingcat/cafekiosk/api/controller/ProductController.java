package com.codingcat.cafekiosk.api.controller;

import com.codingcat.cafekiosk.api.service.product.ProductService;
import com.codingcat.cafekiosk.api.service.product.dto.CreateProductRequest;
import com.codingcat.cafekiosk.api.service.product.dto.ProductResponse;
import com.codingcat.cafekiosk.domain.product.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @PostMapping("/api/v1/products")
  private ProductResponse createProduct(CreateProductRequest request){
    return productService.createProduct(request);
  }


  // 판매 가능한 상품 정보 가져오기
  @GetMapping("/api/v1/products/selling")
  public List<ProductResponse> getSellingProducts(){
    return productService.getSellingProducts();
  }
}
