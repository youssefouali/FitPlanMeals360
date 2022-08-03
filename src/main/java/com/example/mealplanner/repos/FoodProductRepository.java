package com.example.mealplanner.repos;

import com.example.mealplanner.entity.FoodProduct;
import com.example.mealplanner.enums.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FoodProductRepository extends JpaRepository<FoodProduct,Long> {

    @Query("SELECT name FROM FoodProduct where name like %:keyword%")
    List<String> findByNameContaining(@Param("keyword") String keyword);

    FoodProduct findByName(String name);

    List<FoodProduct> findByFoodType(FoodType foodType);
}
