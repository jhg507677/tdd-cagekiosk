package com.codingcat.cafekiosk.api.controller;

import com.codingcat.cafekiosk.api.service.product.ProductService;
import com.codingcat.cafekiosk.api.service.product.dto.ProductResponse;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping("/api/vi/products/selling")
  public List<ProductResponse> getSellingProducts(){
    return productService.getSellingProducts();
  }
}
