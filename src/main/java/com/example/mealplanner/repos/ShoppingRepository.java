package com.example.mealplanner.repos;

import com.example.mealplanner.entity.ShoppingItem;
import com.example.mealplanner.enums.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingRepository extends JpaRepository<ShoppingItem,Long> {

    List<ShoppingItem> findAllByPlanId(long planId);

    List<ShoppingItem> findAllByPlanIdAndIngredientFoodProductNameAndDoneAndIngredientUnitType(long planId,
                                                                                               String foodProductName, boolean isDone, UnitType unitType);
}
