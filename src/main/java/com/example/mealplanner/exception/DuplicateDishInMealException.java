package com.example.mealplanner.exception;

import com.example.mealplanner.entity.Dish;
import com.example.mealplanner.entity.Meal;

public class DuplicateDishInMealException extends RuntimeException{

    public DuplicateDishInMealException(Dish dish, Meal meal) {
        super("Recipe or product name "+ dish.getTitle() +" \n" +
                "is already assigned to: "+meal.getDate()+" " + meal.getMealType().getLabel());
    }
}