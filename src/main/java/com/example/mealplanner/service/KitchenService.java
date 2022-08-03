package com.example.mealplanner.service;

import com.example.mealplanner.entity.KitchenProduct;

import java.util.List;

public interface KitchenService {

    public List<KitchenProduct> getAllProductsForUser(long id);

    public void addProduct(KitchenProduct product);

    public void removeProduct(long id);

    public void removeKitchenProductByFoodProductId(long userId, long id);
}
