package com.example.mealplanner.controller;


import com.example.mealplanner.entity.FoodProduct;
import com.example.mealplanner.entity.KitchenProduct;
import com.example.mealplanner.entity.Recipe;
import com.example.mealplanner.enums.FoodType;
import com.example.mealplanner.security.services.UserDetailsServiceImpl;
import com.example.mealplanner.service.FoodProductService;
import com.example.mealplanner.service.KitchenService;
import com.example.mealplanner.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/kitchen")
public class KitchenController {

    @Autowired
    FoodProductService foodService;

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    KitchenService kitchenService;

    @Autowired
    RecipeService recipeService;

    @GetMapping("/removeProduct")
    public @ResponseBody
    String removeProduct(@RequestParam long foodProductId) {
        FoodProduct foodProduct = foodService.getFoodProduct(foodProductId);
        long userId = userService.getCurrentUserId();
        kitchenService.removeKitchenProductByFoodProductId(userId, foodProduct.getId());
        return "success";
    }


    @GetMapping("/addProduct")
    public @ResponseBody String addProduct(@RequestParam long foodProductId) {
        FoodProduct foodProduct = foodService.getFoodProduct(foodProductId);
        KitchenProduct kitchenProduct = new KitchenProduct();
        kitchenProduct.setUser(userService.getCurrentUser());
        kitchenProduct.setFoodProduct(foodProduct);
        kitchenService.addProduct(kitchenProduct);
        return "success";
    }

    @GetMapping("/getUserProducts")
    public @ResponseBody
    List<KitchenProduct> getUserKitchenProducts(){
        List<KitchenProduct> products =  kitchenService.getAllProductsForUser(userService.getCurrentUserId());
        return products;
    }

    @GetMapping("/getFoodTypes")
    public @ResponseBody FoodType[] getFoodTypes(){
        return FoodType.values();
    }

    @GetMapping("/getAvailableRecipes")
    public @ResponseBody List<Recipe> getRecipesForUsersProducts(){
        List<KitchenProduct> kitchenProducts = kitchenService.getAllProductsForUser(userService.getCurrentUserId());
        return recipeService.getRecipesForUserProducts(kitchenProducts);
    }



}
