package com.example.mealplanner.service;

import com.example.mealplanner.entity.*;
import com.example.mealplanner.enums.MealType;
import com.example.mealplanner.enums.UnitType;
import com.example.mealplanner.payload.request.ShoppingItemDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PlanService {

    Plan getCurrentPlan(User user);

    void save(Plan plan);

    Plan getPlanById(Long id);

    Meal getMeal(long id);

    Meal getMeal(Long planId, LocalDate mealDate, MealType type);

    Plan getPlanByMealId(long mealId);

    List<Plan> getAllUserPlans(long userId);

    List<Plan> getAllUserPlansFrom(long userId, LocalDate date);

    List<Plan> getAllUserPlansUntil(long userId, LocalDate date);

    public void updateShoppingItems(int planId, String ingredientName, boolean isDone, UnitType unitType);

    public Map<String, List<ShoppingItemDTO>> getPreparedShoppingList(long planId);

    void addDishToMeal(Meal meal, Dish dish, int servings);

    void removeDishFromMeal(Meal meal, Dish dish);

    void saveSingleDish(SingleDishProduct singleDish);

    SingleDishProduct getSingleDishProduct(long id);

    void deleteSingleDish(SingleDishProduct singleDishProduct);

    void copyPlanMealsToPlan(Plan planWithMeals, Plan newPlan);
}
