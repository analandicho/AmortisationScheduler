package com.analandicho.AmortisationScheduler.services;

import com.analandicho.AmortisationScheduler.models.LoanAsset;
import com.analandicho.AmortisationScheduler.models.Schedule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
@Service
public class LoanCalculator {
    public BigDecimal getMonthlyRepaymentAmountWithoutBalloon(BigDecimal financedAmount, BigDecimal rate, int termInMonths) {
        return financedAmount.multiply((rate.multiply(BigDecimal.ONE.add(rate).pow(termInMonths)))
                .divide(BigDecimal.ONE.add(rate).pow(termInMonths).subtract(BigDecimal.ONE), 7, RoundingMode.HALF_EVEN));
    }

    public BigDecimal getMonthlyRepaymentAmountWithBalloon(BigDecimal financedAmount, BigDecimal rate, int termInMonths, BigDecimal balloonAmount) {
        BigDecimal leftSide = financedAmount.subtract(balloonAmount.divide(BigDecimal.ONE.add(rate).pow(termInMonths), 7, RoundingMode.HALF_EVEN));
        BigDecimal rightSide = rate.divide(BigDecimal.ONE.subtract(BigDecimal.ONE.add(rate).pow(-termInMonths, MathContext.DECIMAL32)), 7, RoundingMode.HALF_EVEN);

        return leftSide.multiply(rightSide);
    }

    public BigDecimal calculateScheduleInterest(BigDecimal balance, BigDecimal periodicInterest) {
        return balance.multiply(periodicInterest);

    }

    public BigDecimal calculateSchedulePrincipal(BigDecimal totalPayment, BigDecimal interestPayment) {
        return totalPayment.subtract(interestPayment);
    }

    public BigDecimal calculateScheduledPaymentBalance(BigDecimal previousBalance, BigDecimal principalPayment) {
        return previousBalance.subtract(principalPayment);
    }


    public List<Schedule> createAmortisationSchedule(BigDecimal balance, BigDecimal interestRate, int termInMonths, BigDecimal calculatedMonthlyRepayment, LoanAsset loanAsset) {
        List<Schedule> amortisationSchedule = new ArrayList<>();
        BigDecimal offsetBalance = balance;
        for (int period=1; period<=termInMonths; period++) {
            BigDecimal interestPortionAmount = calculateScheduleInterest(offsetBalance, interestRate);
            BigDecimal principalPortionAmount = calculateSchedulePrincipal(calculatedMonthlyRepayment, interestPortionAmount);
            BigDecimal remainingBalance = calculateScheduledPaymentBalance(offsetBalance, principalPortionAmount);

            offsetBalance = remainingBalance; // new remaining balance

            amortisationSchedule.add(new Schedule(
                    period,
                    calculatedMonthlyRepayment,
                    principalPortionAmount,
                    interestPortionAmount,
                    remainingBalance,
                    loanAsset
            ));
        }

       return amortisationSchedule;
    }



}
