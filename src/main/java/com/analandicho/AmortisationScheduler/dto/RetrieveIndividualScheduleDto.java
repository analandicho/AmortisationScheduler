package com.analandicho.AmortisationScheduler.dto;

import java.util.List;

public class RetrieveIndividualScheduleDto {
    private PreviousSchedulesDto details;

    private List<ScheduleDto> amortisationSchedule;

    public RetrieveIndividualScheduleDto(PreviousSchedulesDto details, List<ScheduleDto> amortisationSchedule) {
        this.details = details;
        this.amortisationSchedule = amortisationSchedule;
    }

    public PreviousSchedulesDto getDetails() {
        return details;
    }

    public void setDetails(PreviousSchedulesDto details) {
        this.details = details;
    }

    public List<ScheduleDto> getAmortisationSchedule() {
        return amortisationSchedule;
    }
    public void setAmortisationSchedule(List<ScheduleDto> amortisationSchedule) {
        this.amortisationSchedule = amortisationSchedule;
    }
}
