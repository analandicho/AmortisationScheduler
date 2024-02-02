package com.analandicho.AmortisationScheduler.repositories;

import com.analandicho.AmortisationScheduler.dto.PreviousSchedulesDto;
import com.analandicho.AmortisationScheduler.models.LoanAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LoanAssetRepository extends JpaRepository<LoanAsset, Long> {
    @Query("SELECT new com.analandicho.AmortisationScheduler.dto.PreviousSchedulesDto(la.id, " +
            "la.costAmount, la.depositAmount, la.yearInterestRate, la.numberOfMonthlyPayments, " +
            "la.balloonAmount, la.calculatedRepaymentAmount, SUM(s.interest), SUM(s.payment)) " +
            "FROM LoanAsset la LEFT OUTER JOIN Schedule s ON la.id = s.loanAsset.id GROUP BY la.id ORDER BY la.id")
    List<PreviousSchedulesDto> getPreviousSchedules(); // Get Total Payments and Total Interest Due based from Schedule.

}
