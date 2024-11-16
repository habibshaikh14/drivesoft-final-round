package com.example.drivesoft.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommonUtil {
  private CommonUtil() {
    // Prevent instantiation
  }

  public static BigDecimal parsePrice(String salesPrice) {
    BigDecimal price = null;
    try {
      price = new BigDecimal(salesPrice);
    } catch (Exception e) {
      // Handle exception
    }
    return price;
  }

  public static LocalDate parseDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
    LocalDate parsedDate = null;
    try {
      parsedDate = LocalDate.parse(date, formatter);
    } catch (Exception e) {
      // Handle exception
    }
    return parsedDate;
  }
}
