package com.example.mealplanner.repos;

import com.example.mealplanner.entity.KitchenProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface KitchenRepository  extends JpaRepository<KitchenProduct, Long> {

    List<KitchenProduct> findByUserId(long userId);

    void deleteByUserIdAndFoodProductId(long userId, long id);
}
