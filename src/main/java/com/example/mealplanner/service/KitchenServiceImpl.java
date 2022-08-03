package com.example.mealplanner.service;

import com.example.mealplanner.entity.KitchenProduct;
import com.example.mealplanner.exception.UniqueProductConstraintValidationException;
import com.example.mealplanner.repos.KitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class KitchenServiceImpl implements KitchenService{

    @Autowired
    KitchenRepository kitchenRepository;


    @Override
    public List<KitchenProduct> getAllProductsForUser(long userId) {
        return kitchenRepository.findByUserId(userId);
    }

    @Override
    public void addProduct(KitchenProduct product) {

        try {
            kitchenRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueProductConstraintValidationException(product.getFoodProduct().getName());
        }

    }

    @Override
    public void removeProduct(long id) {

        kitchenRepository.deleteById(id);
    }

    @Override
    public void removeKitchenProductByFoodProductId(long userId, long id) {

        kitchenRepository.deleteByUserIdAndFoodProductId(userId, id);

    }
}
