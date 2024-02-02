package com.analandicho.AmortisationScheduler.repositories;


import com.analandicho.AmortisationScheduler.dto.PreviousSchedulesDto;
import com.analandicho.AmortisationScheduler.models.LoanAsset;
import com.analandicho.AmortisationScheduler.models.Schedule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class) // Note: integrates Spring Text context framework to JUNIT Jupiter 5 (can be heavy and slow)
@DataJpaTest // Loads Spring Data JPA slice of the Spring context above
public class LoanAssetRepositoryTest {

    @Autowired
    private LoanAssetRepository loanAssetRepository;
    private LoanAsset loanAsset;

    private LoanAsset loanAssetWithBalloon;

    @BeforeEach
    public void setUp() {
        loanAsset = new LoanAsset(
                new BigDecimal("25000"),
                new BigDecimal("5000"),
                new BigDecimal("7.5"),
                new BigDecimal("0"),
                60,
                new BigDecimal("400.7580000"));

       loanAssetWithBalloon = new LoanAsset(
                new BigDecimal("25000"),
                new BigDecimal("5000"),
                new BigDecimal("7.5"),
                new BigDecimal("10000"),
                12,
                new BigDecimal("930.07"));
    }

    @AfterEach
    public void tearDown() {
        loanAssetRepository.deleteAll();
        loanAsset = null;
        loanAssetWithBalloon = null;
    }

    @Test
    public void save() {
        loanAssetRepository.save(loanAsset);

        List<LoanAsset> fetchedLoanAssets = loanAssetRepository.findAll();
        assertEquals(1, fetchedLoanAssets.size());
    }

    @Test
    public void saveWithSchedule() {
        List<Schedule> testSchedules = List.of(
                new Schedule(1,
                        new BigDecimal("1735.15"), new BigDecimal("1610.15"), new BigDecimal("125.00"), new BigDecimal("18389.85"), loanAsset
                ));
        loanAsset.setSchedules(testSchedules);

        loanAssetRepository.save(loanAsset);


        List<LoanAsset> fetchedLoanAssets = loanAssetRepository.findAll();
        assertEquals(1, fetchedLoanAssets.size());
        assertEquals(1, fetchedLoanAssets.get(0).getSchedules().size());
    }

    @Test
    public void findById() {
        loanAssetRepository.saveAll(List.of(loanAsset, loanAssetWithBalloon));

        Optional<LoanAsset> fetchedLoanAssets = loanAssetRepository.findById(2L);
        assertFalse(fetchedLoanAssets.isEmpty());
        assertEquals(2, fetchedLoanAssets.get().getId());
    }

    @Test
    public void getPreviousSchedules() {

        List<Schedule> testSchedules = List.of(
                new Schedule(1,
                        new BigDecimal("1735.15"), new BigDecimal("1610.15"), new BigDecimal("125.00"), new BigDecimal("18389.85"), loanAsset
                ));
        loanAsset.setSchedules(testSchedules);


        List<Schedule> testSchedules2 = List.of(
                new Schedule(1, new BigDecimal("930.07"), new BigDecimal("805.07"), new BigDecimal("125.00"), new BigDecimal("19194.93"), loanAssetWithBalloon),
                new Schedule(2, new BigDecimal("930.07"), new BigDecimal("810.11"), new BigDecimal("119.97"), new BigDecimal("18384.82"), loanAssetWithBalloon));

        loanAssetWithBalloon.setSchedules(testSchedules2);

        loanAssetRepository.saveAll(List.of(loanAsset, loanAssetWithBalloon));

        List<PreviousSchedulesDto> fetchedLoanAssets = loanAssetRepository.getPreviousSchedules();
        assertEquals(2, fetchedLoanAssets.size());

        // Check totals for 1st asset schedule
        assertEquals(new BigDecimal("1735.15"),fetchedLoanAssets.get(0).getTotalPaymentDue());
        assertEquals(new BigDecimal("125.00"), fetchedLoanAssets.get(0).getTotalInterestDue());

        // Check totals for 2nd asset schedule
        assertEquals(new BigDecimal("1860.14"),fetchedLoanAssets.get(1).getTotalPaymentDue());
        assertEquals(new BigDecimal("244.97"), fetchedLoanAssets.get(1).getTotalInterestDue());
    }

}
