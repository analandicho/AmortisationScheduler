package com.analandicho.AmortisationScheduler.models;

import com.analandicho.AmortisationScheduler.models.LoanAsset;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int period;

    private BigDecimal payment;
    private BigDecimal principal;

    private BigDecimal interest;

    private BigDecimal balance;

    @ManyToOne // (fetch = FetchType.LAZY)
    @JoinColumn(name="LOAN_ASSET_ID")
    private LoanAsset loanAsset;

    public Schedule() {}


    public Schedule(int period, BigDecimal payment, BigDecimal principal, BigDecimal interest, BigDecimal balance, LoanAsset loanAsset) {
//        this.id = UUID.randomUUID();
        this.period = period;
        this.payment = payment;
        this.principal = principal;
        this.interest = interest;
        this.balance = balance;
        this.loanAsset = loanAsset;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LoanAsset getLoanAsset() {
        return loanAsset;
    }

    public void setLoanAsset(LoanAsset loanAsset) {
        this.loanAsset = loanAsset;
    }
}
