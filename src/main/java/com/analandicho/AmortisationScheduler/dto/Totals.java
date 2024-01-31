package com.analandicho.AmortisationScheduler.dto;

import java.math.BigDecimal;


public class Totals {
    private BigDecimal totalInterestDue;

    private BigDecimal totalPaymentDue;


    public Totals(BigDecimal totalInterestDue, BigDecimal totalPaymentDue) {
        this.totalInterestDue = totalInterestDue;
        this.totalPaymentDue = totalPaymentDue;
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
