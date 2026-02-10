package com.codingcat.cafekiosk.api.service.order;

import com.codingcat.cafekiosk.api.service.order.dto.OrderCreateRequest;
import com.codingcat.cafekiosk.api.service.order.dto.OrderResponse;
import com.codingcat.cafekiosk.domain.order.Order;
import com.codingcat.cafekiosk.domain.order.OrderRepository;
import com.codingcat.cafekiosk.domain.product.Product;
import com.codingcat.cafekiosk.domain.product.ProductRepository;
import com.codingcat.cafekiosk.domain.product.ProductType;
import com.codingcat.cafekiosk.domain.stock.Stock;
import com.codingcat.cafekiosk.domain.stock.StockRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final StockRepository stockRepository;

  public OrderResponse createOrder(
    OrderCreateRequest request, LocalDateTime registeredDateTime
  ) {
    List<String> requestProducts = request.getProductNumbers();

    // 1. 상품 조회
    List<Product> products = findProductsBy(requestProducts);

    // 2. 재고 차감
    deductStockQuantities(products);

    // 3. 주문 생성
    Order order = Order.create(products, registeredDateTime);
    Order savedOrder = orderRepository.save(order);

    return OrderResponse.toOrder(savedOrder);
  }

  /**
   * 상품 조회, 실제 구매할 수 있는 상품인지 체크
   * @param requestProducts : {001, 001, 002}
   * @return
   */
  private List<Product> findProductsBy(
    List<String> requestProducts
  ) {
    // 상품 코드를 가지고 DB에서 주문한 상품 정보 가져옴
    List<Product> dbProducts = productRepository.findAllByProductNumberIn(requestProducts);

    // 상품코드를 key값으로
    Map<String, Product> productMap = dbProducts.stream()
      .collect(Collectors.toMap(Product::getProductNumber, p -> p));

    // 요청 상품들에 DB에서 가져온 상품 정보를 넘겨줌
    return requestProducts.stream()
      .map(productNumber -> {
        Product product = productMap.get(productNumber);
        if (product == null) {
          throw new IllegalArgumentException("존재하지 않는 상품 번호입니다: " + productNumber);
        }
        return product;
      })
      .collect(Collectors.toList());
  }

  /**
   * 재고 차감
   * @param products
   * @param productNumbers
   */
  private void deductStockQuantities(List<Product> products) {
    // 1. 재고 차감이 필요한 상품 번호 추출 ex) 병 음료, 베이커리
    List<String> productsToDeductStock = extractStockProductNumbers(products);

    // 2. 재고 엔티티 조회
    List<Stock> dbStocks = stockRepository.findAllByProductNumberIn(productsToDeductStock);
    Map<String, Stock> sbStockMap = dbStocks.stream()
      .collect(Collectors.toMap(Stock::getProductNumber, s -> s));

    // 3. 상품별 주문 수량 계산 ex) {A101=3, A102=2, A103=1}
    Map<String, Long> productCountMap = productsToDeductStock.stream()
      .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

    // 4. 재고 차감
    for (Map.Entry<String, Long> entry : productCountMap.entrySet()) {
      String productNumber = entry.getKey();
      int quantity = entry.getValue().intValue();

      Stock stock = sbStockMap.get(productNumber);
      if (stock == null || stock.isQuantityLessThanRequestQuantity(quantity)) {
        throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
      }
      stock.deductQuantity(quantity);
    }
  }

  private List<String> extractStockProductNumbers(List<Product> products) {
    return products.stream()
      .filter(product -> ProductType.containsStockType(product.getType()))
      .map(Product::getProductNumber)
      .collect(Collectors.toList());
  }
}