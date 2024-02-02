package com.analandicho.AmortisationScheduler.dto;


import java.math.BigDecimal;

public record ScheduleDto(
        int period,
        BigDecimal payment,
        BigDecimal principal,
        BigDecimal interest,
        BigDecimal balance

) {

//    private int period;
//
//    private BigDecimal payment;
//
//    private BigDecimal principal;
//    private BigDecimal interest;
//
//    private BigDecimal balance;

//    public int getPeriod() {
//        return period;
//    }
//
//    public void setPeriod(int period) {
//        this.period = period;
//    }
//
//    public BigDecimal getPayment() {
//        return payment;
//    }
//
//    public void setPayment(BigDecimal payment) {
//        this.payment = payment;
//    }
//
//    public BigDecimal getPrincipal() {
//        return principal;
//    }
//
//    public void setPrincipal(BigDecimal principal) {
//        this.principal = principal;
//    }
//
//    public BigDecimal getInterest() {
//        return interest;
//    }
//
//    public void setInterest(BigDecimal interest) {
//        this.interest = interest;
//    }
//
//    public BigDecimal getBalance() {
//        return balance;
//    }
//
//    public void setBalance(BigDecimal balance) {
//        this.balance = balance;
//    }


}
