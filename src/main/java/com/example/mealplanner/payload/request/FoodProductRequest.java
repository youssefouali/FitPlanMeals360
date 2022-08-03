package com.example.mealplanner.payload.request;

import com.example.mealplanner.entity.Nutrition;
import com.example.mealplanner.enums.ERole;
import com.example.mealplanner.enums.FoodType;

public class FoodProductRequest {

    private String name;

    private FoodType foodType;

    private Nutrition nutrition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }
}
