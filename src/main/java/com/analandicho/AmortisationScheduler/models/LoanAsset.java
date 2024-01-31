package com.analandicho.AmortisationScheduler.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Table to store details about the asset being loaned
@Entity
public class LoanAsset {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private BigDecimal costAmount;

    private BigDecimal depositAmount;

    private BigDecimal yearInterestRate;

    private BigDecimal balloonAmount;

    private int numberOfMonthlyPayments;

    private BigDecimal calculatedRepaymentAmount;

    @OneToMany(mappedBy = "loanAsset", cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    LoanAsset() {
    }

    public LoanAsset(BigDecimal costAmount, BigDecimal depositAmount, BigDecimal yearInterestRate, BigDecimal balloonAmount, int numberOfMonthlyPayments, BigDecimal calculatedRepaymentAmount) {
        this.id = UUID.randomUUID();
        this.costAmount = costAmount;
        this.depositAmount = depositAmount;
        this.yearInterestRate = yearInterestRate;
        this.balloonAmount = balloonAmount;
        this.numberOfMonthlyPayments = numberOfMonthlyPayments;
        this.calculatedRepaymentAmount = calculatedRepaymentAmount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getYearInterestRate() {
        return yearInterestRate;
    }

    public void setYearInterestRate(BigDecimal yearInterestRate) {
        this.yearInterestRate = yearInterestRate;
    }

    public BigDecimal getBalloonAmount() {
        return balloonAmount;
    }

    public void setBalloonAmount(BigDecimal balloonAmount) {
        this.balloonAmount = balloonAmount;
    }

    public int getNumberOfMonthlyPayments() {
        return numberOfMonthlyPayments;
    }

    public void setNumberOfMonthlyPayments(int numberOfMonthlyPayments) {
        this.numberOfMonthlyPayments = numberOfMonthlyPayments;
    }

    public BigDecimal getCalculatedRepaymentAmount() {
        return calculatedRepaymentAmount;
    }

    public void setCalculatedRepaymentAmount(BigDecimal calculatedRepaymentAmount) {
        this.calculatedRepaymentAmount = calculatedRepaymentAmount;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }
}
