package com.example.mealplanner.repos;

import com.example.mealplanner.entity.SingleDishProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleDishProductRepository extends JpaRepository<SingleDishProduct, Long> {
}
