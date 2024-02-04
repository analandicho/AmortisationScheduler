package com.analandicho.AmortisationScheduler.services;

import com.analandicho.AmortisationScheduler.dto.*;
import com.analandicho.AmortisationScheduler.exception.AssetScheduleNotFoundException;
import com.analandicho.AmortisationScheduler.exception.InvalidInputException;
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

    @Autowired
    LoanCalculator loanCalculator;


    public CreateScheduleResponse createScheduleForLoan(LoanAssetRequest loanDTO) throws Exception {

        if (loanDTO.costAmount().intValue() < 0 || loanDTO.depositAmount().intValue() < 0 || loanDTO.balloonAmount().intValue() < 0) {
            throw new InvalidInputException("Invalid input: Amount value supplied is less than 0");
        }
        BigDecimal financedAmount = loanDTO.costAmount().subtract(loanDTO.depositAmount());
        BigDecimal rate = loanDTO.yearInterestRate().divide(new BigDecimal("1200"), 6, RoundingMode.HALF_EVEN);
        int termInMonths = loanDTO.numberOfMonthlyPayments();
        BigDecimal balloonAmount = loanDTO.balloonAmount();


        // Calculate Monthly Repayment:
        BigDecimal calculatedMonthlyRepayment = balloonAmount.equals(BigDecimal.ZERO) ?
                loanCalculator.getMonthlyRepaymentAmountWithoutBalloon(financedAmount, rate, termInMonths) :
                loanCalculator.getMonthlyRepaymentAmountWithBalloon(financedAmount, rate, termInMonths, balloonAmount);


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

    public RetrieveIndividualScheduleDto getIndividualSchedule(Long assetID) throws AssetScheduleNotFoundException {

       Optional<LoanAsset> matchedAsset = loanAssetRepository.findById(assetID);
       if (matchedAsset.isEmpty()) {
           throw new AssetScheduleNotFoundException("Asset " + assetID + " is not found.");
       }

       Totals totalsDue = scheduleRepository.getTotalsOfAssetSchedule(assetID); // IMPROVEMENT?
       if (totalsDue==null) {
           throw new AssetScheduleNotFoundException("No amortisation schedule created yet for asset: " + assetID);
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