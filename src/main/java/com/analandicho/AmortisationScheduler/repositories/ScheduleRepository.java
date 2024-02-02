package com.analandicho.AmortisationScheduler.repositories;


import com.analandicho.AmortisationScheduler.dto.Totals;
import com.analandicho.AmortisationScheduler.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT new com.analandicho.AmortisationScheduler.dto.Totals(SUM(s.interest), SUM(s.payment)) FROM Schedule s WHERE s.loanAsset.id = :loanAssetId")
    Totals getTotalsOfAssetSchedule(@Param("loanAssetId") Long loanAssetId);
}
