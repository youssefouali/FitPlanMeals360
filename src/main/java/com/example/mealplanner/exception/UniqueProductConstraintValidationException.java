package com.example.mealplanner.exception;

public class UniqueProductConstraintValidationException extends RuntimeException {

    public UniqueProductConstraintValidationException(String name) {
        super("\n" +
                "there is already a product named: "+ name);
    }

}