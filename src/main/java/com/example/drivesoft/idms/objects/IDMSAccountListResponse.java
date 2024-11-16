package com.example.drivesoft.idms.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IDMSAccountListResponse {
  @JsonProperty("Status")
  private String status;

  @JsonProperty("Message")
  private String message;

  @JsonProperty("Data")
  private List<IDMSAccountRowWrapper> data;
}



