package com.analandicho.AmortisationScheduler.services;

import com.analandicho.AmortisationScheduler.dto.*;
import com.analandicho.AmortisationScheduler.models.LoanAsset;
import com.analandicho.AmortisationScheduler.models.Schedule;
import com.analandicho.AmortisationScheduler.repositories.LoanAssetRepository;
import com.analandicho.AmortisationScheduler.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class AmortisationScheduleService {
    @Autowired
    LoanAssetRepository loanAssetRepository;

    @Autowired
    ScheduleRepository scheduleRepository;


    public CreateScheduleResponse createScheduleForLoan(LoanAssetRequest loanDTO) throws Exception {
        LoanCalculator loanCalculator = new LoanCalculator();

//        BigDecimal financedAmount = loanDTO.getCostAmount().subtract(loanDTO.getDepositAmount());
//        BigDecimal rate = loanDTO.getYearInterestRate().divide(new BigDecimal("1200"), 6, RoundingMode.HALF_EVEN);
//        int termInMonths = loanDTO.getNumberOfMonthlyPayments();
//        BigDecimal balloonAmount = loanDTO.getBalloonAmount();

        BigDecimal financedAmount = loanDTO.costAmount().subtract(loanDTO.depositAmount());
        BigDecimal rate = loanDTO.yearInterestRate().divide(new BigDecimal("1200"), 6, RoundingMode.HALF_EVEN);
        int termInMonths = loanDTO.numberOfMonthlyPayments();
        BigDecimal balloonAmount = loanDTO.balloonAmount();

        // TODO: check for values; Do we also check if Loan Asset Amount is 0 ???
        if (balloonAmount.intValue() < 0) {
            throw new Exception("Balloon Amount cannot be 0");
        }

        // Calculate Monthly Repayment:
        BigDecimal calculatedMonthlyRepayment = balloonAmount.equals(BigDecimal.ZERO) ?
                loanCalculator.getMonthlyRepaymentAmountWithoutBalloon(financedAmount, rate, termInMonths) :
                loanCalculator.getMonthlyRepaymentAmountWithBalloon(financedAmount, rate, termInMonths, balloonAmount);


//        LoanAsset loanAsset = new LoanAsset(loanDTO.getCostAmount(),
//                loanDTO.getDepositAmount(),
//                loanDTO.getYearInterestRate(),
//                balloonAmount,
//                termInMonths,
//                calculatedMonthlyRepayment);

        LoanAsset loanAsset = new LoanAsset(loanDTO.costAmount(),
                loanDTO.depositAmount(),
                loanDTO.yearInterestRate(),
                balloonAmount,
                termInMonths,
                calculatedMonthlyRepayment);

        // Create the Amortisation Schedule:
        List<Schedule> amortisationSchedule = loanCalculator.createAmortisationSchedule(financedAmount, rate, termInMonths, calculatedMonthlyRepayment, loanAsset);

        // Link schedules to Asset
        loanAsset.setSchedules(amortisationSchedule);

        // Save loan asset and amortisation schedules:
        LoanAsset result = loanAssetRepository.save(loanAsset);
        return new CreateScheduleResponse(result.getId());

    }

    public List<PreviousSchedulesDto> listGeneratedScheduleDetails() {
        List<PreviousSchedulesDto> assetListWithDueTotals = loanAssetRepository.getPreviousSchedules();
        if (assetListWithDueTotals.isEmpty()) {
            return Collections.emptyList();
        }

        return assetListWithDueTotals;
    }

    public RetrieveIndividualScheduleDto getIndividualSchedule(Long assetID) {

       Optional<LoanAsset> matchedAsset = loanAssetRepository.findById(assetID);
       if (matchedAsset.isEmpty()) {
           throw new Error("Unable to find asset details for ID: " + assetID);
       }

       Totals totalsDue = scheduleRepository.getTotalsOfAssetSchedule(assetID); // Get SUMS. Any other way in Hibernate to do this?
       if (totalsDue==null) {
           throw new Error("No amortisation schedule found for asset: " + assetID);
       }

       LoanAsset asset = matchedAsset.get();
       ScheduleService scheduleService = new ScheduleService();

        return new RetrieveIndividualScheduleDto(
                new PreviousSchedulesDto(asset.getId(),
                        asset.getCostAmount(),
                        asset.getDepositAmount(),
                        asset.getYearInterestRate(),
                        asset.getNumberOfMonthlyPayments(),
                        asset.getBalloonAmount(),
                        asset.getCalculatedRepaymentAmount(),
                        totalsDue.getTotalInterestDue(),
                        totalsDue.getTotalPaymentDue())
                , asset.getSchedules().stream().map(scheduleService::convertEntityToDto).toList()

        );
    }
}