package com.example.drivesoft.idms.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The {@code IDMSAccountListResponse} class represents the response structure for an account list
 * retrieved from the IDMS (Identity Management System). This response contains the status of the request,
 * a message providing additional details, and a list of account data.
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Data} - Lombok annotation that automatically generates getter, setter, toString, equals, and hashCode methods.</li>
 * <li>{@code @NoArgsConstructor} - Lombok annotation that generates a no-argument constructor.</li>
 * <li>{@code @AllArgsConstructor} - Lombok annotation that generates a constructor with arguments for all fields.</li>
 * <li>{@code @JsonProperty} - Jackson annotation used to map the Java fields to JSON property names.</li>
 * </ul>
 *
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IDMSAccountListResponse {

  /**
   * The status of the account list request, typically a string indicating whether the request was successful
   * or if there was an error.
   * Example: "Success", "Error".
   */
  @JsonProperty("Status")
  private String status;

  /**
   * A message providing more details about the request status.
   * This message could contain additional information or error details.
   * Example: "Request was successful", "Invalid request parameters".
   */
  @JsonProperty("Message")
  private String message;

  /**
   * The list of account data rows returned by the IDMS system. Each row contains information about an individual account.
   * This list can be empty or contain multiple account entries, depending on the response.
   */
  @JsonProperty("Data")
  private List<IDMSAccountRowWrapper> data;
}
