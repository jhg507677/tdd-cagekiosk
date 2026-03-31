package com.codingcat.cafekiosk.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codingcat.cafekiosk.domain.product.ProductSellingStatus;
import com.codingcat.cafekiosk.domain.product.ProductService;
import com.codingcat.cafekiosk.domain.product.ProductType;
import com.codingcat.cafekiosk.domain.product.dto.CreateProductRequest;
import com.codingcat.cafekiosk.domain.product.dto.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;
  @MockBean ProductService productService;

  @DisplayName("================ 신규 상품 생성 OK Param================ ")
  @Test
  void createProduct() throws Exception {
    // given
    String url = "/api/v1/products";
    CreateProductRequest request = CreateProductRequest.builder()
      .type(ProductType.HANDMADE)
      .sellingStatus(ProductSellingStatus.SELLING)
      .name("아메리카노")
      .price(4000)
      .build();

    // when // then
    mockMvc.perform(post(url)
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON)
    ).andDo(print())
      .andExpect(status().isOk());
  }

  @DisplayName("================ 신규 상품 생성 No Param ProductType ================ ")
  @Test
  void createProductWithoutType() throws Exception {
    // given
    when(productService.getSellingProducts()).thenReturn(List.of());

    String url = "/api/v1/products";
    CreateProductRequest request = CreateProductRequest.builder()
      .sellingStatus(ProductSellingStatus.SELLING)
      .name("아메리카노")
      .price(4000)
      .build();

    // when // then
    mockMvc.perform(post(url)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
      ).andDo(print())
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
      .andExpect(jsonPath("$.data").isEmpty())
    ;
  }

  @DisplayName("================ 판매상품 조회 ================ ")
  @Test
  void test() throws Exception {
    List<ProductResponse> result = List.of();
    when(productService.getSellingProducts()).thenReturn(result);
    // given
    String url = "/api/v1/products/selling";

    // when // then
    mockMvc.perform(get(url)
      ).andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data").isArray())
    ;
  }
}