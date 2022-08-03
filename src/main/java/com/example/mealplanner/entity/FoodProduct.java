package com.example.mealplanner.entity;

import com.example.mealplanner.enums.FoodType;
import com.example.mealplanner.enums.UnitType;

import javax.persistence.*;

@Entity
@Table(name = "foodProduct")
public class FoodProduct {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nutrition_id",referencedColumnName = "id")
    private Nutrition nutrition;

    public FoodProduct() {
    }

    public FoodProduct(long id, String name, FoodType foodType, Nutrition nutrition) {
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.nutrition = nutrition;
    }

    public FoodProduct(String name, FoodType foodType, Nutrition nutrition) {
        this.name = name;
        this.foodType = foodType;
        this.nutrition = nutrition;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Nutrition getNutritionPerUnitType(UnitType unitType) {
        return this.getNutrition().getNutritionPerUnitType(unitType);
    }

}
