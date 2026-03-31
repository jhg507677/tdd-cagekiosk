package com.codingcat.cafekiosk.controller;

import com.codingcat.cafekiosk.domain.product.ProductService;
import com.codingcat.cafekiosk.domain.product.dto.CreateProductRequest;
import com.codingcat.cafekiosk.domain.product.dto.ProductResponse;
import com.codingcat.cafekiosk.module.model.ApiResponseVo;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  // 상품 등록
  @PostMapping("/api/v1/products")
  private ApiResponseVo<ProductResponse> createProduct(
    @RequestBody @Valid CreateProductRequest request
  ){
    return ApiResponseVo.ok(productService.createProduct(request));
  }

  // 판매 가능한 상품 정보 가져오기
  @GetMapping("/api/v1/products/selling")
  public ApiResponseVo<List<ProductResponse>> getSellingProducts(){
    return ApiResponseVo.ok(productService.getSellingProducts());
  }
}
