package com.example.mealplanner.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MealType {
    BREAKFAST("\n" +
            "Breakfast"),
    LUNCH("\n" +
            "Lunch"),
    DINNER("Dinner"),
    SNACKS("\n" +
            "Snacks");

    public final String label;

    private MealType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return this.name();
    }
}
