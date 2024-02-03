package com.analandicho.AmortisationScheduler.controllers;

import com.analandicho.AmortisationScheduler.dto.CreateScheduleResponse;
import com.analandicho.AmortisationScheduler.dto.LoanAssetRequest;
import com.analandicho.AmortisationScheduler.dto.PreviousSchedulesDto;
import com.analandicho.AmortisationScheduler.services.AmortisationScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedules")
public class AmortisationScheduleController {

    @Autowired
    AmortisationScheduleService amortisationScheduleService;
    @PutMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity createAmortisationScheduleForLoan(@RequestBody LoanAssetRequest loanDetails) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(amortisationScheduleService.createScheduleForLoan(loanDetails));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

    }

    @GetMapping("/view/all")
    public ResponseEntity<List<PreviousSchedulesDto>> listGeneratedScheduleDetails() {

        return ResponseEntity.ok(amortisationScheduleService.listGeneratedScheduleDetails());
    }

    @GetMapping("/view/{assetId}")
    public ResponseEntity getIndividualAmortisationSchedule(
            @PathVariable("assetId") Long assetId
    ) {
//        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(amortisationScheduleService.getIndividualSchedule(assetId));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }


    }

}
