package com.example.drivesoft.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Version
  private Long version;

  @Column(name = "contract_sales_price")
  private BigDecimal contractSalesPrice;

  @Column(name = "acct_type")
  private String acctType;

  @Column(name = "sales_group_person1_id")
  private String salesGroupPerson1ID;

  @Column(name = "contract_date")
  private LocalDate contractDate;

  @Column(name = "collateral_stock_number")
  private String collateralStockNumber;

  @Column(name = "collateral_year_model")
  private String collateralYearModel;

  @Column(name = "collateral_make")
  private String collateralMake;

  @Column(name = "collateral_model")
  private String collateralModel;

  @Column(name = "borrower1_first_name")
  private String borrower1FirstName;

  @Column(name = "borrower1_last_name")
  private String borrower1LastName;

  @Column(name = "acct_id", nullable = false, unique = true)
  private String acctID;
}

