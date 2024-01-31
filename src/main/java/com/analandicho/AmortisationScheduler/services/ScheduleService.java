package com.analandicho.AmortisationScheduler.services;

import com.analandicho.AmortisationScheduler.dto.ScheduleDto;
import com.analandicho.AmortisationScheduler.models.Schedule;

public class ScheduleService {

    public ScheduleDto convertEntityToDto(Schedule schedule) {
        ScheduleDto dto = new ScheduleDto();
        dto.setPeriod(schedule.getPeriod());
        dto.setPayment(schedule.getPayment());
        dto.setPrincipal(schedule.getPrincipal());
        dto.setInterest(schedule.getInterest());
        dto.setBalance(schedule.getBalance());
        return dto;
    }
}
