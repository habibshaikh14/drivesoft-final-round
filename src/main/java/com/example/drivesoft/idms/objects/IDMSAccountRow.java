package com.example.drivesoft.idms.objects;

import com.example.drivesoft.account.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.drivesoft.utils.CommonUtil.parseDate;
import static com.example.drivesoft.utils.CommonUtil.parsePrice;

/**
 * The {@code IDMSAccountRow} class represents a single account row retrieved from the IDMS (Identity Management System).
 * This row contains various details related to a specific account, such as contract sales price, collateral information,
 * and borrower details. It also provides a method to map the row data to an {@link Account} entity.
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
public class IDMSAccountRow {

  /**
   * The sales price of the contract associated with the account.
   * This field is parsed to a numeric format in the {@link #mapToEntity()} method.
   */
  @JsonProperty("ContractSalesPrice")
  private String contractSalesPrice;

  /**
   * The type of the account.
   * Example: "Loan", "Lease", etc.
   */
  @JsonProperty("AcctType")
  private String acctType;

  /**
   * The ID of the person associated with the sales group for the account.
   */
  @JsonProperty("SalesGroupPerson1ID")
  private String salesGroupPerson1ID;

  /**
   * The date when the contract for the account was signed.
   * This field is parsed into a {@link java.util.Date} object in the {@link #mapToEntity()} method.
   */
  @JsonProperty("ContractDate")
  private String contractDate;

  /**
   * The stock number of the collateral associated with the account.
   */
  @JsonProperty("CollateralStockNumber")
  private String collateralStockNumber;

  /**
   * The year and model of the collateral associated with the account.
   */
  @JsonProperty("CollateralYearModel")
  private String collateralYearModel;

  /**
   * The make of the collateral associated with the account.
   */
  @JsonProperty("CollateralMake")
  private String collateralMake;

  /**
   * The model of the collateral associated with the account.
   */
  @JsonProperty("CollateralModel")
  private String collateralModel;

  /**
   * The first name of the primary borrower for the account.
   */
  @JsonProperty("Borrower1FirstName")
  private String borrower1FirstName;

  /**
   * The last name of the primary borrower for the account.
   */
  @JsonProperty("Borrower1LastName")
  private String borrower1LastName;

  /**
   * The account ID for the account.
   */
  @JsonProperty("AcctID")
  private String acctID;

  /**
   * Maps the account row to an {@link Account} entity by setting the appropriate fields
   * based on the values in the account row.
   * The method parses the contract sales price and contract date, and maps the other details directly.
   *
   * @return A new {@link Account} entity populated with the details from this account row.
   */
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
