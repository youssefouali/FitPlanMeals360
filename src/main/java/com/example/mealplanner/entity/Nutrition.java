package com.example.mealplanner.entity;

import com.example.mealplanner.enums.UnitType;

import javax.persistence.*;
import java.util.List;

@Entity
public class Nutrition {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private float density;

    @Column
    private float kcal;
    @Column
    private float protein;
    @Column
    private float carbs;
    @Column
    private float fat;


   /* @OneToOne(mappedBy = "nutrition")
    private FoodProduct foodProduct;*/

    public Nutrition() {
    }

    public Nutrition(/*long id*/float density, float kcal, float protein, float carbs, float fat /*FoodProduct foodProduct*/) {
       /* this.id = id;*/
        this.density = density;
        this.kcal = kcal;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
       /* this.foodProduct = foodProduct;*/
    }

    public Nutrition(float kcal, float protein, float carbs, float fat) {
        this.kcal = kcal;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getKcal() {
        return kcal;
    }

    public void setKcal(float kcal) {
        this.kcal = kcal;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

   /* public FoodProduct getFoodProduct() {
        return foodProduct;
    }

    public void setFoodProduct(FoodProduct foodProduct) {
        this.foodProduct = foodProduct;
    }*/

    public Nutrition getNutritionPerUnitType(UnitType unitType) {
        float conversionCoeff = 1;
        switch (unitType) {
            case GRAM:
                conversionCoeff = conversionCoeff / 100;
                break;
            case KILOGRAM:
                conversionCoeff = (conversionCoeff / 100) * 1000;
                break;
            case MILILITRE:
                conversionCoeff = (conversionCoeff / 100) * density;
                break;
            case LITRE:
                conversionCoeff = (conversionCoeff / 100) * density * 1000;
                break;
            case CUP:
                conversionCoeff = (conversionCoeff / 100) * density * 250;
                break;
            case TABLE_SPOON:
                conversionCoeff = (conversionCoeff / 100) * density * 15;
                break;
            case TEA_SPOON:
                conversionCoeff = (conversionCoeff / 100) * density * 5;
                break;
            default:
                break;
        }
        Nutrition nutrition = new Nutrition();
        nutrition.setKcal(this.getKcal() * conversionCoeff);
        nutrition.setProtein(this.getProtein() * conversionCoeff);
        nutrition.setCarbs(this.getCarbs() * conversionCoeff);
        nutrition.setFat(this.getFat() * conversionCoeff);
        return nutrition;
    }

    public static Nutrition sumNutritions(List<Nutrition> nutritions) {
        float kcal = 0, carbs = 0, fat = 0, protein = 0;
        for (Nutrition nutrition : nutritions) {
            kcal = kcal + nutrition.getKcal();
            carbs = carbs + nutrition.getCarbs();
            fat = fat + nutrition.getFat();
            protein = protein + nutrition.getProtein();
        }
        return new Nutrition(kcal, protein, carbs, fat);
    }

    public static Nutrition multiplyNutritionsByFloat(Nutrition nutrition, float arg) {
        nutrition.setKcal(nutrition.getKcal() * arg);
        nutrition.setCarbs(nutrition.getCarbs() * arg);
        nutrition.setFat(nutrition.getFat() * arg);
        nutrition.setProtein(nutrition.getProtein() * arg);
        return nutrition;
    }

    public static Nutrition divideNutritionsByLong(Nutrition nutrition, long arg) {
        nutrition.setKcal(nutrition.getKcal() / arg);
        nutrition.setCarbs(nutrition.getCarbs() / arg);
        nutrition.setFat(nutrition.getFat() / arg);
        nutrition.setProtein(nutrition.getProtein() / arg);
        return nutrition;
    }
}
