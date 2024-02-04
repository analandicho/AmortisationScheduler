package com.analandicho.AmortisationScheduler.dto;


import java.math.BigDecimal;

public record ScheduleDto(
        int period,
        BigDecimal payment,
        BigDecimal principal,
        BigDecimal interest,
        BigDecimal balance

) {

}
