package com.example.mealplanner.repos;

import com.example.mealplanner.entity.Meal;
import com.example.mealplanner.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    Meal findByPlanIdAndDateAndMealType(Long planId, LocalDate date, MealType mealType);
}
