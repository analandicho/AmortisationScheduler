package com.analandicho.AmortisationScheduler.dto;

import java.math.BigDecimal;

public record LoanAssetRequest (
        BigDecimal costAmount,
        BigDecimal depositAmount,
        BigDecimal yearInterestRate,
        int numberOfMonthlyPayments,
        BigDecimal balloonAmount

                                ) {

    // TODO:
//    private BigDecimal costAmount;
//
//    private BigDecimal depositAmount;
//
//    private BigDecimal yearInterestRate;
//
//    private int numberOfMonthlyPayments;
//    private BigDecimal balloonAmount;


//    public BigDecimal getCostAmount() {
//        return costAmount;
//    }
//
//    public void setCostAmount(BigDecimal costAmount) {
//        this.costAmount = costAmount;
//    }
//
//    public BigDecimal getDepositAmount() {
//        return depositAmount;
//    }
//
//    public void setDepositAmount(BigDecimal depositAmount) {
//        this.depositAmount = depositAmount;
//    }
//
//    public BigDecimal getYearInterestRate() {
//        return yearInterestRate;
//    }
//
//    public void setYearInterestRate(BigDecimal yearInterestRate) {
//        this.yearInterestRate = yearInterestRate;
//    }
//
//    public int getNumberOfMonthlyPayments() {
//        return numberOfMonthlyPayments;
//    }
//
//    public void setNumberOfMonthlyPayments(int numberOfMonthlyPayments) {
//        this.numberOfMonthlyPayments = numberOfMonthlyPayments;
//    }
//
//    public BigDecimal getBalloonAmount() {
//        return balloonAmount;
//    }
//
//    public void setBalloonAmount(BigDecimal balloonAmount) {
//        this.balloonAmount = balloonAmount;
//    }
}
