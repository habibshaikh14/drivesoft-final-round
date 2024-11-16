package com.example.drivesoft.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The {@code AccountDTO} class is a Data Transfer Object (DTO) that is used to transfer
 * account-related data between layers in a controlled and structured manner.
 * <p>
 * This class ensures that only the required fields are exposed while other fields,
 * such as identifiers or internal versioning, remain hidden during serialization.
 * </p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @JsonProperty} - Maps Java fields to JSON properties for serialization and deserialization.</li>
 * <li>{@code @JsonIgnore} - Excludes fields from JSON output.</li>
 * <li>Lombok annotations such as {@code @Data}, {@code @AllArgsConstructor}, and {@code @NoArgsConstructor} are used
 * to reduce boilerplate code.</li>
 * </ul>
 *
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

  /**
   * The unique identifier of the account.
   * This field is ignored during JSON serialization.
   */
  @JsonIgnore
  private Long id;

  /**
   * Version number used for optimistic locking.
   * This field is ignored during JSON serialization.
   */
  @JsonIgnore
  private Long version;

  /**
   * The sales price of the contract associated with the account.
   */
  @JsonProperty("contract_sales_price")
  private BigDecimal contractSalesPrice;

  /**
   * The type of account (e.g., personal, business).
   */
  @JsonProperty("acct_type")
  private String acctType;

  /**
   * Identifier for the first sales group person associated with the account.
   */
  @JsonProperty("sales_group_person1_id")
  private String salesGroupPerson1ID;

  /**
   * The contract date for the account.
   */
  @JsonProperty("contract_date")
  private LocalDate contractDate;

  /**
   * Stock number of the collateral.
   */
  @JsonProperty("collateral_stock_number")
  private String collateralStockNumber;

  /**
   * The year model of the collateral.
   */
  @JsonProperty("collateral_year_model")
  private String collateralYearModel;

  /**
   * The make of the collateral.
   */
  @JsonProperty("collateral_make")
  private String collateralMake;

  /**
   * The model of the collateral.
   */
  @JsonProperty("collateral_model")
  private String collateralModel;

  /**
   * First name of the primary borrower.
   */
  @JsonProperty("borrower1_first_name")
  private String borrower1FirstName;

  /**
   * Last name of the primary borrower.
   */
  @JsonProperty("borrower1_last_name")
  private String borrower1LastName;

  /**
   * Unique account ID associated with the account.
   */
  @JsonProperty("acct_id")
  private String acctID;
}
