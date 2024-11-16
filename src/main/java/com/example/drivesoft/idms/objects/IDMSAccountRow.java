package com.example.drivesoft.idms.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IDMSAccountRow {
  @JsonProperty("ContractSalesPrice")
  private String contractSalesPrice;

  @JsonProperty("AcctType")
  private String acctType;

  @JsonProperty("SalesGroupPerson1ID")
  private String salesGroupPerson1ID;

  @JsonProperty("ContractDate")
  private String contractDate;

  @JsonProperty("CollateralStockNumber")
  private String collateralStockNumber;

  @JsonProperty("CollateralYearModel")
  private String collateralYearModel;

  @JsonProperty("CollateralMake")
  private String collateralMake;

  @JsonProperty("CollateralModel")
  private String collateralModel;

  @JsonProperty("Borrower1FirstName")
  private String borrower1FirstName;

  @JsonProperty("Borrower1LastName")
  private String borrower1LastName;

  @JsonProperty("AcctID")
  private String acctID;
}