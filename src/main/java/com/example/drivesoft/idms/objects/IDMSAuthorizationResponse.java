package com.example.drivesoft.idms.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code IDMSAuthorizationResponse} class represents the structure of the response received
 * from the IDMS (Identity Management System) after an authorization request.
 * This response contains the status, a token for further requests, and an associated message.
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Data} - Lombok annotation that generates getter, setter, toString, equals, and hashCode methods.</li>
 * <li>{@code @NoArgsConstructor} - Lombok annotation that generates a no-argument constructor.</li>
 * <li>{@code @AllArgsConstructor} - Lombok annotation that generates a constructor with arguments for all fields.</li>
 * <li>{@code @JsonProperty} - Jackson annotation used to map Java fields to JSON property names during serialization and deserialization.</li>
 * </ul>
 *
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IDMSAuthorizationResponse {

  /**
   * The status of the authorization request.
   * Typically, this is an HTTP status code or a specific application-defined status indicating
   * whether the authorization was successful.
   */
  @JsonProperty("Status")
  private int status;

  /**
   * The token returned by the IDMS system after successful authorization.
   * This token is typically used for further authentication or authorization in subsequent requests.
   */
  @JsonProperty("Token")
  private String token;

  /**
   * A message providing additional information about the authorization result.
   * This can be a success message or an error message explaining any issues encountered.
   */
  @JsonProperty("Message")
  private String message;
}
