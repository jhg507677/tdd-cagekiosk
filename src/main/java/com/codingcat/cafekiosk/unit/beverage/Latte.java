package com.codingcat.cafekiosk.unit.beverage;

import com.codingcat.cafekiosk.unit.beverage.Beverage;

public class Latte implements Beverage {

  @Override
  public String getName() {
    return "라떼";
  }

  @Override
  public int getPrice() {
    return 4500;
  }
}
