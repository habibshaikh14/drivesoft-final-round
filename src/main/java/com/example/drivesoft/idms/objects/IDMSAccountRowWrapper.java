package com.example.drivesoft.idms.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code IDMSAccountRowWrapper} class is used to wrap an individual {@link IDMSAccountRow} object.
 * This wrapper is necessary for the JSON structure returned from the IDMS (Identity Management System),
 * where each row of account data is encapsulated in a {@code "Row"} field.
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Data} - Lombok annotation that generates getter, setter, toString, equals, and hashCode methods.</li>
 * <li>{@code @NoArgsConstructor} - Lombok annotation that generates a no-argument constructor.</li>
 * <li>{@code @AllArgsConstructor} - Lombok annotation that generates a constructor with arguments for all fields.</li>
 * <li>{@code @JsonProperty} - Jackson annotation used to map Java fields to JSON property names.</li>
 * </ul>
 *
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IDMSAccountRowWrapper {

  /**
   * The account row data. This contains all the information about a single account,
   * including contract details, collateral, and borrower information.
   */
  @JsonProperty("Row")
  private IDMSAccountRow row;
}
