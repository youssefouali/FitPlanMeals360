package com.example.mealplanner.entity;

import com.example.mealplanner.enums.FoodType;
import com.example.mealplanner.enums.UnitType;

import javax.persistence.*;

@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private float ammount;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private UnitType unitType;

    @ManyToOne
    @JoinColumn(name = "foodProductId")
    private FoodProduct foodProduct;


    public Ingredient() {
    }

    public Ingredient(long id, float ammount, String name, UnitType unitType, FoodProduct foodProduct) {
        this.id = id;
        this.ammount = ammount;
        this.name = name;
        this.unitType = unitType;
        this.foodProduct = foodProduct;
    }

    public Ingredient(float ammount, String name, UnitType unitType, FoodProduct foodProduct) {
        this.ammount = ammount;
        this.name = name;
        this.unitType = unitType;
        this.foodProduct = foodProduct;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getAmmount() {
        return ammount;
    }

    public void setAmmount(float ammount) {
        this.ammount = ammount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public FoodProduct getFoodProduct() {
        return foodProduct;
    }

    public void setFoodProduct(FoodProduct foodProduct) {
        this.foodProduct = foodProduct;
    }

    public Nutrition getNutritionForIngredient() {
        return Nutrition.multiplyNutritionsByFloat(foodProduct.getNutritionPerUnitType(unitType), ammount);
    }

}
