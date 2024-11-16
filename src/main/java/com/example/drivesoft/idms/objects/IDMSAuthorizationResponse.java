package com.example.drivesoft.idms.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IDMSAuthorizationResponse {

  @JsonProperty("Status")
  private int status;

  @JsonProperty("Token")
  private String token;

  @JsonProperty("Message")
  private String message;
}
