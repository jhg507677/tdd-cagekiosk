package com.codingcat.cafekiosk.api.controller;

import com.codingcat.cafekiosk.api.service.product.ProductService;
import com.codingcat.cafekiosk.api.service.product.dto.ProductResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping("/api/v1/products/selling")
  public List<ProductResponse> getSellingProducts(){
    return productService.getSellingProducts();
  }
}
