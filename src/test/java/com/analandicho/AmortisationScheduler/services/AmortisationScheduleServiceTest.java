package com.analandicho.AmortisationScheduler.services;


import com.analandicho.AmortisationScheduler.dto.*;
import com.analandicho.AmortisationScheduler.exception.AssetScheduleNotFoundException;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AmortisationScheduleServiceTest {

    @Mock
    private LoanAssetRepository loanAssetRepository;


    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private LoanCalculator loanCalculator;

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

    @DisplayName("createScheduleForLoan: create schedule with no balloon amount, verify correct monthly repayment calculation with no balloon amount is called.")
    @Test
    public void shouldCreateScheduleForGivenLoanWithNoBalloonAmount() throws Exception {
        LoanAssetRequest requestDto = new LoanAssetRequest(
                costAmount,
                depositAmount,
                yearInterestRate,
                termInMonths,
                balloonAmount
        );
        LoanAsset loanAssetEntity = getMockLoanAsset(balloonAmount);
        loanAssetEntity.setId(1L);

        when(loanAssetRepository.save(any(LoanAsset.class))).thenReturn(loanAssetEntity);

        CreateScheduleResponse crResponse = new CreateScheduleResponse(loanAssetId);

        var actual = amortisationScheduleService.createScheduleForLoan(requestDto);

        assertEquals(1L, actual.getAssetId());
        assertEquals("Schedule creation is successful", actual.getStatusMessage());

        verify(loanAssetRepository, times(1)).save(any(LoanAsset.class));
        verify(loanCalculator, times(1)).getMonthlyRepaymentAmountWithoutBalloon(
                costAmount.subtract(depositAmount),
                yearInterestRate.divide(new BigDecimal("1200"), 6, RoundingMode.HALF_EVEN),
                termInMonths);
    }

    @DisplayName("createScheduleForLoan: create schedule with balloon amount, verify correct monthly repayment calculation with balloon amount is called.")
    @Test
    public void shouldCreateScheduleForGivenLoanWithBalloonAmount() throws Exception {
        BigDecimal nonZeroBalloonAmount = new BigDecimal("10000");

        LoanAssetRequest requestDto = new LoanAssetRequest(
                costAmount,
                depositAmount,
                yearInterestRate,
                termInMonths,
                nonZeroBalloonAmount
        );
        LoanAsset loanAssetEntity = getMockLoanAsset(nonZeroBalloonAmount);
        loanAssetEntity.setId(1L);

        when(loanAssetRepository.save(any(LoanAsset.class))).thenReturn(loanAssetEntity);

        CreateScheduleResponse crResponse = new CreateScheduleResponse(loanAssetId);

        var actual = amortisationScheduleService.createScheduleForLoan(requestDto);

        assertEquals(1L, actual.getAssetId());
        assertEquals("Schedule creation is successful", actual.getStatusMessage());

        verify(loanAssetRepository, times(1)).save(any(LoanAsset.class));
        verify(loanCalculator, times(1)).getMonthlyRepaymentAmountWithBalloon(
                costAmount.subtract(depositAmount),
                yearInterestRate.divide(new BigDecimal("1200"), 6, RoundingMode.HALF_EVEN),
                termInMonths,
                nonZeroBalloonAmount);
    }

    @DisplayName("listGeneratedScheduleDetails: get list of previously created schedule details with total payment and interest due.")
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

    @DisplayName("listGeneratedScheduleDetails: get empty list when no created schedules")
    @Test
    public void shouldGetEmptyListWhenNoCreatedSchedules() {
        when(loanAssetRepository.getPreviousSchedules()).thenReturn(
                Collections.emptyList()
        );

        var actual = amortisationScheduleService.listGeneratedScheduleDetails();

        assertEquals(0, actual.size());
        verify(loanAssetRepository, times(1)).getPreviousSchedules();
    }

    @DisplayName("getIndividualSchedule: get individual schedule for a given asset ID")
    @Test // TODO
    public void shouldGetIndividualSchedule() throws Exception {
        PreviousSchedulesDto schedulesDto = getMockPreviousSchedulesDto();
        LoanAsset loanAsset = getMockLoanAsset(balloonAmount);
        loanAsset.setId(1L);
        List<ScheduleDto> schedules = List.of(getMockScheduleDto());


        RetrieveIndividualScheduleDto risDto = new RetrieveIndividualScheduleDto(
                schedulesDto,
                schedules
        );


        when(loanAssetRepository.findById(loanAssetId)).thenReturn(Optional.of(loanAsset));
        when(scheduleRepository.getTotalsOfAssetSchedule(loanAssetId)).thenReturn(new Totals(
                new BigDecimal("12"),
                new BigDecimal("13")
        ));

        var actual = amortisationScheduleService.getIndividualSchedule(loanAssetId);

        System.out.println(actual.getDetails().getCostAmount());

        assertEquals(1, actual.getDetails().getLoanAssetId());

        verify(loanAssetRepository, times(1)).findById(loanAssetId);
        verify(scheduleRepository, times(1)).getTotalsOfAssetSchedule(loanAssetId);
    }

    @DisplayName("getIndividualSchedule: throw exception when no matching asset ID is found")
    @Test
    public void shouldThrowExceptionWhenNoMatchingAssetSchedules() throws Exception {
        PreviousSchedulesDto schedulesDto = getMockPreviousSchedulesDto();
        LoanAsset loanAsset = getMockLoanAsset(balloonAmount);
        loanAsset.setId(1L);
        List<ScheduleDto> schedules = List.of(getMockScheduleDto());


        RetrieveIndividualScheduleDto risDto = new RetrieveIndividualScheduleDto(
                schedulesDto,
                schedules
        );

        String exceptionMsg = "No match asset id found";
        when(loanAssetRepository.findById(3L)).thenThrow(new AssetScheduleNotFoundException(exceptionMsg));

        Exception exception = assertThrows(AssetScheduleNotFoundException.class, () -> amortisationScheduleService.getIndividualSchedule(3L));

        assertEquals(exceptionMsg, exception.getMessage());

        verify(loanAssetRepository, times(1)).findById(loanAssetId); // TODO
        verify(scheduleRepository, times(0)).getTotalsOfAssetSchedule(loanAssetId);
    }


    @DisplayName("getIndividualSchedule: throw exception when no schedules found for a given asset ID")
    @Test
    public void shouldThrowExceptionWhenNoSchedulesFoundForAsset() throws Exception {
        PreviousSchedulesDto schedulesDto = getMockPreviousSchedulesDto();
        LoanAsset loanAsset = getMockLoanAsset(balloonAmount);
        loanAsset.setId(1L);
        List<ScheduleDto> schedules = List.of(getMockScheduleDto());


        RetrieveIndividualScheduleDto risDto = new RetrieveIndividualScheduleDto(
                schedulesDto,
                schedules
        );

        String exceptionMsg = "No schedules for given asset ID";
        when(loanAssetRepository.findById(loanAssetId)).thenReturn(Optional.of(getMockLoanAsset(balloonAmount)));
        when(scheduleRepository.getTotalsOfAssetSchedule(loanAssetId)).thenThrow(new AssetScheduleNotFoundException(exceptionMsg));

        Exception exception = assertThrows(AssetScheduleNotFoundException.class, () -> amortisationScheduleService.getIndividualSchedule(loanAssetId));
        String actualMessage = exception.getMessage();

        assertEquals(exceptionMsg, exception.getMessage());

        verify(loanAssetRepository, times(1)).findById(loanAssetId);
        verify(scheduleRepository, times(1)).getTotalsOfAssetSchedule(loanAssetId);
    }



    private LoanAsset getMockLoanAsset(BigDecimal balloonPayment) {
        return new LoanAsset(
                costAmount,
                depositAmount,
                yearInterestRate,
                balloonPayment,
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
