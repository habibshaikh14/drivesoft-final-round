package com.example.drivesoft.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The {@code Account} class represents an entity for managing account information.
 * It includes various account-related fields, such as contract details, borrower details,
 * and collateral information.
 * <p>
 * This entity is cached for improved performance using Hibernate's second-level caching.
 * </p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Entity} - Specifies that this class is an entity mapped to the database.</li>
 * <li>{@code @Table(name = "account")} - Specifies the table name for this entity.</li>
 * <li>{@code @Cache} - Configures Hibernate caching with READ_WRITE strategy.</li>
 * </ul>
 *
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account", indexes = @Index(name = "idx_acct_id", columnList = "acct_id"))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Account {

  /**
   * The unique identifier for the account entity.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Version field for optimistic locking.
   * It is marked as {@code @Transient} to exclude it from persistence.
   */
  @Version
  @Transient
  private Long version;

  /**
   * The sales price of the contract associated with the account.
   */
  @Column(name = "contract_sales_price")
  private BigDecimal contractSalesPrice;

  /**
   * The type of account (e.g., personal, business).
   */
  @Column(name = "acct_type")
  private String acctType;

  /**
   * Identifier for the first sales group person associated with the account.
   */
  @Column(name = "sales_group_person1_id")
  private String salesGroupPerson1ID;

  /**
   * The contract date for the account.
   */
  @Column(name = "contract_date")
  private LocalDate contractDate;

  /**
   * Stock number of the collateral.
   */
  @Column(name = "collateral_stock_number")
  private String collateralStockNumber;

  /**
   * The year model of the collateral.
   */
  @Column(name = "collateral_year_model")
  private String collateralYearModel;

  /**
   * The make of the collateral.
   */
  @Column(name = "collateral_make")
  private String collateralMake;

  /**
   * The model of the collateral.
   */
  @Column(name = "collateral_model")
  private String collateralModel;

  /**
   * First name of the primary borrower.
   */
  @Column(name = "borrower1_first_name")
  private String borrower1FirstName;

  /**
   * Last name of the primary borrower.
   */
  @Column(name = "borrower1_last_name")
  private String borrower1LastName;

  /**
   * Unique account ID.
   * This field is non-nullable and must be unique.
   * It is indexed for faster retrieval.
   */
  @Column(name = "acct_id", nullable = false, unique = true)
  private String acctID;

  /**
   * Converts the current {@code Account} entity to an {@code AccountDTO}.
   * <p>
   * This method is used for transferring data without exposing entity-specific details.
   * </p>
   *
   * @return an {@code AccountDTO} representation of this entity
   */
  public AccountDTO toDTO() {
    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setId(id);
    accountDTO.setVersion(version);
    accountDTO.setContractSalesPrice(contractSalesPrice);
    accountDTO.setAcctType(acctType);
    accountDTO.setSalesGroupPerson1ID(salesGroupPerson1ID);
    accountDTO.setContractDate(contractDate);
    accountDTO.setCollateralStockNumber(collateralStockNumber);
    accountDTO.setCollateralYearModel(collateralYearModel);
    accountDTO.setCollateralMake(collateralMake);
    accountDTO.setCollateralModel(collateralModel);
    accountDTO.setBorrower1FirstName(borrower1FirstName);
    accountDTO.setBorrower1LastName(borrower1LastName);
    accountDTO.setAcctID(acctID);
    return accountDTO;
  }
}
