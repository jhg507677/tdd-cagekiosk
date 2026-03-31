package com.codingcat.cafekiosk.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codingcat.cafekiosk.domain.order.OrderService;
import com.codingcat.cafekiosk.domain.order.OrderStatus;
import com.codingcat.cafekiosk.domain.order.dto.OrderCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;
  @MockBean OrderService orderService;

  @DisplayName("================ 주문하기: success ================ ")
  @Test
  void test() throws Exception {
    // given
    String url = "/api/v1/orders";
    OrderCreateRequest request = OrderCreateRequest.builder()
      .productNumbers(List.of("001"))
      .build();

    // when // then
    mockMvc.perform(MockMvcRequestBuilders.post(url)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
      ).andDo(print())
      .andExpect(status().isOk())
    ;
  }

  @DisplayName("================ 주문하기 fail: 상품 번호 입력 안함 ================ ")
  @Test
  void createOrderWithEmptyProductNumbers() throws Exception {
    // given
    String url = "/api/v1/orders";
    OrderCreateRequest request = OrderCreateRequest.builder()
      .productNumbers(List.of())
      .build();

    // when // then
    mockMvc.perform(MockMvcRequestBuilders.post(url)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
      ).andDo(print())
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message").value("상품 번호 리스트는 필수입니다."))
    ;
  }
}