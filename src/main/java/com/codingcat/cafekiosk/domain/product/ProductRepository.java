package com.codingcat.cafekiosk.domain.product;

import com.codingcat.cafekiosk.domain.product.Product;
import com.codingcat.cafekiosk.domain.product.ProductSellingStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  /*** SELECT * FROM WHERE selling_status IN ("SELLING","HOLD");*/
  List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);

  /*** SELECT * FROM WHERE product_number IN (입력값들);*/
  List<Product> findAllByProductNumberIn(List<String> productNumbers);
}
