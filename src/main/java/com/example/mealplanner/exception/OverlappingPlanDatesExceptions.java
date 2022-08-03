package com.example.mealplanner.exception;

import java.time.LocalDate;

public class OverlappingPlanDatesExceptions extends RuntimeException {
    public OverlappingPlanDatesExceptions(LocalDate startDate, LocalDate endDate) {
        super("There is already a plan for at least one day in this interval\n: " + startDate + " - " + endDate);
    }
}
