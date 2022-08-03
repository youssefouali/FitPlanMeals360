package com.example.mealplanner.service;

import com.example.mealplanner.entity.FoodProduct;
import com.example.mealplanner.enums.FoodType;
import com.example.mealplanner.repos.FoodProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodProductServiceImpl implements FoodProductService{

    @Autowired
    FoodProductRepository foodProductRepository;

    @Override
    public FoodProduct getFoodProduct(String name) {
        return foodProductRepository.findByName(name);
    }

    @Override
    public void addFoodProduct(FoodProduct foodProduct) {
        foodProductRepository.save(foodProduct);
    }

    @Override
    public List<FoodProduct> getFoodProductsByType(FoodType foodType) {
        return foodProductRepository.findByFoodType(foodType);
    }

    @Override
    public List<FoodProduct> getFoodProducts() {
        return foodProductRepository.findAll();
    }

    @Override
    public FoodProduct getFoodProduct(long foodProductId) {
        Optional<FoodProduct> result = foodProductRepository.findById(foodProductId);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    @Override
    public void deleteFoodProduct(long id) {
        foodProductRepository.deleteById(id);
    }
}
