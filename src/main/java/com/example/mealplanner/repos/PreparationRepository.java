package com.example.mealplanner.repos;

import com.example.mealplanner.entity.Preparation;
import com.example.mealplanner.entity.Recipe;
import com.example.mealplanner.enums.MealType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreparationRepository extends JpaRepository<Preparation,Long> {

}
