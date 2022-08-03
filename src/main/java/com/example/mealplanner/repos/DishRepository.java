package com.example.mealplanner.repos;

import com.example.mealplanner.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish,Long> {
}
