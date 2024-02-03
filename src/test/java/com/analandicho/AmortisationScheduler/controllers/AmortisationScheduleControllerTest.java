package com.analandicho.AmortisationScheduler.controllers;

import com.analandicho.AmortisationScheduler.dto.*;
import com.analandicho.AmortisationScheduler.services.AmortisationScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AmortisationScheduleController.class)
public class AmortisationScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    void setUp() {
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

    @Test
    void viewAllEndpoint_shouldReturnEmptyArrayWhenEmpty() throws Exception {
        List<PreviousSchedulesDto>  previousSchedulesDtoList = List.of(new PreviousSchedulesDto());

        when(amortisationScheduleService.listGeneratedScheduleDetails()).thenReturn(previousSchedulesDtoList);

        MvcResult results = this.mockMvc.perform(get("/schedules/view/all")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        ObjectWriter objectWriter = new ObjectMapper().writer();

        String mockJsonResponse = objectWriter.writeValueAsString(previousSchedulesDtoList);
        assertEquals(mockJsonResponse, results.getResponse().getContentAsString());
    }


    @Test // TODO: check.
    void viewAllEndpoint_shouldReturnArrayOfExistingSchedules() throws Exception {
        List<PreviousSchedulesDto>  previousSchedulesDtoList = List.of(getMockPreviousSchedulesDto());


        when(amortisationScheduleService.listGeneratedScheduleDetails()).thenReturn(previousSchedulesDtoList);

        MvcResult results = this.mockMvc.perform(get("/schedules/view/all")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        ObjectWriter objectWriter = new ObjectMapper().writer();

        String mockJsonResponse = objectWriter.writeValueAsString(previousSchedulesDtoList);
        assertEquals(mockJsonResponse, results.getResponse().getContentAsString());

    }


    @Test
    void viewIndividualSched_shouldReturnEmpty() throws Exception {

        RetrieveIndividualScheduleDto mockDto = new RetrieveIndividualScheduleDto(
               getMockPreviousSchedulesDto(),
                List.of( getMockScheduleDto())
        );


        RetrieveIndividualScheduleDto func = amortisationScheduleService.getIndividualSchedule(loanAssetId);

        when(func).thenReturn(mockDto);


        MvcResult results = this.mockMvc.perform(get("/schedules/view/{assetId}", loanAssetId)).andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        ObjectWriter objectWriter = new ObjectMapper().writer();

        String mockJsonResponse = objectWriter.writeValueAsString(mockDto);
        assertEquals(mockJsonResponse, results.getResponse().getContentAsString());


    }

    @Test
    void createSchedule() throws Exception {
        CreateScheduleResponse mockResponse = new CreateScheduleResponse(loanAssetId);

        LoanAssetRequest loanAssetDto = new LoanAssetRequest(
                costAmount,
                depositAmount,
                yearInterestRate,
                termInMonths,
                balloonAmount
        );

        when(amortisationScheduleService.createScheduleForLoan(loanAssetDto)).thenReturn(mockResponse);


        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(loanAssetDto);

        MvcResult results = this.mockMvc.perform(put("/schedules/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();


        ObjectWriter objectWriter = new ObjectMapper().writer();

        String mockResponseInJson = objectWriter.writeValueAsString(mockResponse);
        assertEquals(mockResponseInJson, results.getResponse().getContentAsString());

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
        return   new ScheduleDto(
                1,
                new BigDecimal("175"),
                new BigDecimal("115"),
                new BigDecimal("123"),
                new BigDecimal("1212")
        );

    }

}
