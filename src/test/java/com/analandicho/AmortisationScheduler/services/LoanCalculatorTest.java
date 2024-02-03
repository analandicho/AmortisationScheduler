package com.analandicho.AmortisationScheduler.services;

import com.analandicho.AmortisationScheduler.models.LoanAsset;
import com.analandicho.AmortisationScheduler.models.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import  static org.junit.jupiter.api.Assertions.assertEquals;


// MOCKS and STUBS

// TODO:

public class LoanCalculatorTest {

    BigDecimal financedAmount;
    BigDecimal rate;
    int termInMonths;
    BigDecimal balloonAmount;

    LoanCalculator loanCalculator = new LoanCalculator();


    @BeforeEach
    void setUp() {
        financedAmount = new BigDecimal("20000");
        rate = new BigDecimal("0.00625");
        termInMonths = 60;
        balloonAmount = new BigDecimal("10000");
    }

    @Test
    void createAmortisationSchedule_testWithOutBalloon() { // without Balloon
        BigDecimal assetCost = new BigDecimal("25000");
        BigDecimal deposit = new BigDecimal("5000");
        BigDecimal interestRate = new BigDecimal("7.5");
        int term = 60;
        BigDecimal balloonAmt = new BigDecimal("0");
        BigDecimal repayMonthlyAmtWithOutBalloon = new BigDecimal("400.7580000");

        LoanAsset loanAsset = new LoanAsset(
                assetCost,
                deposit,
                interestRate,
                balloonAmt,
                term,
                repayMonthlyAmtWithOutBalloon
        );

        List<Schedule> amortisationSched = loanCalculator.createAmortisationSchedule(
                assetCost.subtract(deposit),
                interestRate.divide(new BigDecimal("1200"), 6, RoundingMode.HALF_EVEN),
                term,
                repayMonthlyAmtWithOutBalloon,
                loanAsset);


        assertEquals(60, amortisationSched.size());
        assertEquals(new BigDecimal("0"), amortisationSched.get(amortisationSched.size()-1).getBalance(), "Last balance should be 0.00" );

    }


    @Test
    void createAmortisationSchedule_testWithBalloon() { // without Balloon
        BigDecimal assetCost = new BigDecimal("25000");
        BigDecimal deposit = new BigDecimal("5000");
        BigDecimal interestRate = new BigDecimal("7.5");
        int term = 60;
        BigDecimal balloonAmt = new BigDecimal("10000");
        BigDecimal repayMonthlyAmtWithBalloon = new BigDecimal("262.87884842675706");

        LoanAsset loanAsset = new LoanAsset(
                assetCost,
                deposit,
                interestRate,
                balloonAmt,
                term,
                repayMonthlyAmtWithBalloon
        );

        List<Schedule> amortisationSched = loanCalculator.createAmortisationSchedule(
                assetCost.subtract(deposit),
                interestRate.divide(new BigDecimal("1200"), 6, RoundingMode.HALF_EVEN),
                term,
                repayMonthlyAmtWithBalloon,
                loanAsset);


        assertEquals(60, amortisationSched.size());
        assertEquals(new BigDecimal("10000.00"), amortisationSched.get(amortisationSched.size()-1).getBalance(), "Last balance should be balloon amount" );

    }

    @Test
    void getMonthlyRepaymentAmountWithoutBalloon_Test() {

        BigDecimal monthlyRepayment = loanCalculator.getMonthlyRepaymentAmountWithoutBalloon(
                financedAmount,
                rate,
                termInMonths);

        assertEquals(new BigDecimal("400.7580000"), monthlyRepayment);

    }

    @Test
    void getMonthlyRepaymentAmountWithBalloon_Test() {
        BigDecimal balloonAmount = new BigDecimal("10000");


        BigDecimal monthlyRepaymentWithBalloon = loanCalculator.getMonthlyRepaymentAmountWithBalloon(
                financedAmount,
                rate,
                termInMonths,
                balloonAmount);

        assertEquals(new BigDecimal("262.87884842675706"), monthlyRepaymentWithBalloon);
    }

    @Test
    void calculateScheduleInterest() {
        BigDecimal res = loanCalculator.calculateScheduleInterest(financedAmount, rate);
        assertEquals(new BigDecimal("125.00000"), res);
    }

    @Test
    void calculateSchedulePrincipal(){
        BigDecimal principal = loanCalculator.calculateSchedulePrincipal(
                new BigDecimal("200"),
                new BigDecimal("75"));

        assertEquals(new BigDecimal("125"), principal);
    }


}
