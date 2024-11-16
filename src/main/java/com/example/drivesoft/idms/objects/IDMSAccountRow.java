package com.example.drivesoft.idms.objects;

import com.example.drivesoft.account.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.drivesoft.utils.CommonUtil.parseDate;
import static com.example.drivesoft.utils.CommonUtil.parsePrice;

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

  public Account mapToEntity() {
    Account account = new Account();
    account.setContractSalesPrice(parsePrice(contractSalesPrice));
    account.setAcctType(acctType);
    account.setSalesGroupPerson1ID(salesGroupPerson1ID);
    account.setContractDate(parseDate(contractDate));
    account.setCollateralStockNumber(collateralStockNumber);
    account.setCollateralYearModel(collateralYearModel);
    account.setCollateralMake(collateralMake);
    account.setCollateralModel(collateralModel);
    account.setBorrower1FirstName(borrower1FirstName);
    account.setBorrower1LastName(borrower1LastName);
    account.setAcctID(acctID);
    return account;
  }
}