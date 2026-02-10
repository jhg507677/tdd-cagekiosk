package com.codingcat.cafekiosk.domain.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  /*** SELECT * FROM WHERE selling_status IN ("SELLING","HOLD");*/
  List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);

  /*** SELECT * FROM WHERE product_number IN (입력값들);*/
  List<Product> findAllByProductNumberIn(List<String> productNumbers);

  @Query(value = "SELECT product_number FROM product ORDER BY created_date_time DESC LIMIT 1", nativeQuery = true)
  String findLatestProductNumber();
}
