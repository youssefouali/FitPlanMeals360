package com.example.mealplanner.service;

import com.example.mealplanner.entity.FoodProduct;
import com.example.mealplanner.enums.FoodType;

import java.util.List;

public interface FoodProductService {

    public List<FoodProduct> getFoodProductsByType(FoodType foodType);

    public List<FoodProduct> getFoodProducts();

    public FoodProduct getFoodProduct(long foodProductId);

    public FoodProduct getFoodProduct(String name);

    public void addFoodProduct(FoodProduct foodProduct);

    public void deleteFoodProduct(long id);
}
