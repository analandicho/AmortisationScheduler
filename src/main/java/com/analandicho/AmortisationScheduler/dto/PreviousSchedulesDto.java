package com.analandicho.AmortisationScheduler.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class PreviousSchedulesDto {
    private Long loanAssetId;
    private BigDecimal costAmount;
    private BigDecimal depositAmount;
    private BigDecimal yearInterestRate;
    private int numberOfMonthlyPayments;
    private BigDecimal balloonAmount;
    private BigDecimal calculatedRepaymentAmount;
    private BigDecimal totalInterestDue;
    private BigDecimal totalPaymentDue;

    public PreviousSchedulesDto() {

    }

    public PreviousSchedulesDto(Long loanAssetId, BigDecimal costAmount, BigDecimal depositAmount, BigDecimal yearInterestRate, int numberOfMonthlyPayments, BigDecimal balloonAmount, BigDecimal calculatedRepaymentAmount, BigDecimal totalInterestDue, BigDecimal totalPaymentDue) {
        this.loanAssetId = loanAssetId;
        this.costAmount = costAmount;
        this.depositAmount = depositAmount;
        this.yearInterestRate = yearInterestRate;
        this.numberOfMonthlyPayments = numberOfMonthlyPayments;
        this.balloonAmount = balloonAmount;
        this.calculatedRepaymentAmount = calculatedRepaymentAmount;
        this.totalInterestDue = totalInterestDue;
        this.totalPaymentDue = totalPaymentDue;
    }

    public Long getLoanAssetId() {
        return loanAssetId;
    }

    public void setLoanAssetId(Long loanAssetId) {
        this.loanAssetId = loanAssetId;
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

    public int getNumberOfMonthlyPayments() {
        return numberOfMonthlyPayments;
    }

    public void setNumberOfMonthlyPayments(int numberOfMonthlyPayments) {
        this.numberOfMonthlyPayments = numberOfMonthlyPayments;
    }

    public BigDecimal getBalloonAmount() {
        return balloonAmount;
    }

    public void setBalloonAmount(BigDecimal balloonAmount) {
        this.balloonAmount = balloonAmount;
    }

    public BigDecimal getCalculatedRepaymentAmount() {
        return calculatedRepaymentAmount;
    }

    public void setCalculatedRepaymentAmount(BigDecimal calculatedRepaymentAmount) {
        this.calculatedRepaymentAmount = calculatedRepaymentAmount;
    }

    public BigDecimal getTotalInterestDue() {
        return totalInterestDue;
    }

    public void setTotalInterestDue(BigDecimal totalInterestDue) {
        this.totalInterestDue = totalInterestDue;
    }

    public BigDecimal getTotalPaymentDue() {
        return totalPaymentDue;
    }

    public void setTotalPaymentDue(BigDecimal totalPaymentDue) {
        this.totalPaymentDue = totalPaymentDue;
    }

}
