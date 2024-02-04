package com.analandicho.AmortisationScheduler.dto;

import java.math.BigDecimal;

public record LoanAssetRequest (
        BigDecimal costAmount,
        BigDecimal depositAmount,
        BigDecimal yearInterestRate,
        int numberOfMonthlyPayments,
        BigDecimal balloonAmount
) {

}
