package com.analandicho.AmortisationScheduler.services;


import com.analandicho.AmortisationScheduler.dto.PreviousSchedulesDto;
import com.analandicho.AmortisationScheduler.dto.RetrieveIndividualScheduleDto;
import com.analandicho.AmortisationScheduler.dto.ScheduleDto;
import com.analandicho.AmortisationScheduler.dto.Totals;
import com.analandicho.AmortisationScheduler.models.LoanAsset;
import com.analandicho.AmortisationScheduler.models.Schedule;
import com.analandicho.AmortisationScheduler.repositories.LoanAssetRepository;
import com.analandicho.AmortisationScheduler.repositories.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AmortisationScheduleServiceTest {

    @Mock
    private LoanAssetRepository loanAssetRepository;


    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private AmortisationScheduleService amortisationScheduleService;

    Long loanAssetId;
    BigDecimal costAmount;
    BigDecimal depositAmount;
    BigDecimal yearInterestRate;

    int termInMonths;

    BigDecimal balloonAmount;
    BigDecimal calculatedRepayment;
    BigDecimal totalPaymentDue;
    BigDecimal totalInterestDue;


    @BeforeEach
    public void setUp() {
        loanAssetId = 1L;
        costAmount = new BigDecimal("25000");
        depositAmount = new BigDecimal("5000");
        yearInterestRate = new BigDecimal("7.5");
        termInMonths = 12;
        balloonAmount = new BigDecimal("0");
        calculatedRepayment = new BigDecimal("400.25");
        totalPaymentDue = new BigDecimal("17500");
        totalInterestDue = new BigDecimal("250");

    }

    @DisplayName("Get individual schedule given an asset ID")
    @Test
    public void shouldGetIndividualSchedule() {
        PreviousSchedulesDto schedulesDto = getMockPreviousSchedulesDto();
        LoanAsset loanAsset = getMockLoanAsset();
        loanAsset.setId(1L);
        List<ScheduleDto> schedules = List.of(getMockScheduleDto());


        RetrieveIndividualScheduleDto risDto = new RetrieveIndividualScheduleDto(
                schedulesDto,
                schedules
        );



//        loanAsset.setSchedules(List.of());
        when(loanAssetRepository.findById(loanAssetId)).thenReturn(Optional.of(loanAsset));
        when(scheduleRepository.getTotalsOfAssetSchedule(loanAssetId)).thenReturn(new Totals(
                new BigDecimal("12"),
                new BigDecimal("13")
        ));

        var actual = amortisationScheduleService.getIndividualSchedule(loanAssetId);

        System.out.println(actual.getDetails().getCostAmount());

//        assertEquals(schedulesDto, actual.getDetails());
        assertEquals(1, actual.getDetails().getLoanAssetId());

        verify(loanAssetRepository, times(1)).findById(loanAssetId);
        verify(scheduleRepository, times(1)).getTotalsOfAssetSchedule(loanAssetId);





    }

    @DisplayName("Get list of previously created schedule details with total payment and interest due.")
    @Test
    public void shouldGetListOfPreviouslyCreatedSchedules() {
        // Arrange
        List<PreviousSchedulesDto>  previousSchedulesDtoList = List.of(getMockPreviousSchedulesDto());

        when(loanAssetRepository.getPreviousSchedules()).thenReturn(
               previousSchedulesDtoList
        );

        // Act
        var actual = amortisationScheduleService.listGeneratedScheduleDetails();


        // Assert
        assertEquals(1, actual.size());
        assertEquals(loanAssetId, actual.get(0).getLoanAssetId());
        verify(loanAssetRepository, times(1)).getPreviousSchedules();
    }

    @DisplayName("Get empty list when no created schedules")
    @Test
    public void shouldGetEmptyListWhenNoCreatedSchedules() {
        when(loanAssetRepository.getPreviousSchedules()).thenReturn(
                Collections.emptyList()
        );

        var actual = amortisationScheduleService.listGeneratedScheduleDetails();

        assertEquals(0, actual.size());
        verify(loanAssetRepository, times(1)).getPreviousSchedules();
    }


    private LoanAsset getMockLoanAsset() {
        return new LoanAsset(
                costAmount,
                depositAmount,
                yearInterestRate,
                balloonAmount,
                termInMonths,
                calculatedRepayment

        );
    }

    private PreviousSchedulesDto getMockPreviousSchedulesDto() {
        return new PreviousSchedulesDto(
                loanAssetId,
                costAmount,
                depositAmount,
                yearInterestRate,
                termInMonths,
                balloonAmount,
                calculatedRepayment,
                totalPaymentDue,
                totalInterestDue
        );
    }

    private ScheduleDto getMockScheduleDto() {
        return new ScheduleDto(1,
                new BigDecimal("120"),
                new BigDecimal("120"),
                new BigDecimal("120"),
                new BigDecimal("120"));
    }




}
