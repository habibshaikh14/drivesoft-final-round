package com.example.drivesoft.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The {@code CommonUtil} class provides utility methods for common operations.
 * <p>
 * This utility class includes methods for parsing price strings into {@link BigDecimal}
 * and parsing date strings into {@link LocalDate}. The class is not intended to be instantiated.
 * </p>
 *
 * <p>Key Methods:</p>
 * <ul>
 * <li>{@code parsePrice} - Parses a string representing a price into a {@link BigDecimal}.</li>
 * <li>{@code parseDate} - Parses a string representing a date into a {@link LocalDate}.</li>
 * </ul>
 *
 * @since 1.0
 */
public class CommonUtil {

  // Private constructor to prevent instantiation of the utility class.
  private CommonUtil() {
    // Prevent instantiation
  }

  /**
   * Parses a string representing a sales price into a {@link BigDecimal}.
   * <p>
   * This method attempts to convert the input string into a {@link BigDecimal}. If the string is
   * not a valid representation of a number, it returns {@code null}.
   * </p>
   *
   * @param salesPrice the string representation of the sales price
   * @return the {@link BigDecimal} representation of the sales price, or {@code null} if the input is invalid
   */
  public static BigDecimal parsePrice(String salesPrice) {
    BigDecimal price = null;
    try {
      price = new BigDecimal(salesPrice);
    } catch (Exception e) {
      // Handle exception: In this case, we return null if parsing fails
    }
    return price;
  }

  /**
   * Parses a string representing a date into a {@link LocalDate}.
   * <p>
   * This method converts the input string into a {@link LocalDate} using the specified format
   * "MM/dd/yyyy hh:mm:ss a". If the string does not match this format, it returns {@code null}.
   * </p>
   *
   * @param date the string representation of the date
   * @return the {@link LocalDate} representation of the date, or {@code null} if the input is invalid
   */
  public static LocalDate parseDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
    LocalDate parsedDate = null;
    try {
      parsedDate = LocalDate.parse(date, formatter);
    } catch (Exception e) {
      // Handle exception: In this case, we return null if parsing fails
    }
    return parsedDate;
  }
}
