package com.example.mealplanner.controller;


import com.example.mealplanner.entity.FoodProduct;
import com.example.mealplanner.payload.request.FoodProductRequest;
import com.example.mealplanner.payload.response.MessageResponse;
import com.example.mealplanner.service.FoodProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/foodProduct")
public class FoodProductController {

    @Autowired
    FoodProductService foodProductService;

    @GetMapping("/getAll")
    public @ResponseBody
    List<FoodProduct> getFoodProducts() {
        return foodProductService.getFoodProducts();
    }

    @PostMapping("/addFoodProduct")
    public ResponseEntity<?> addFoodProduct(@Valid @RequestBody FoodProductRequest foodProductRequest/*, BindingResult bindingResult*/){
        FoodProduct foodProduct = new FoodProduct(foodProductRequest.getName(),foodProductRequest.getFoodType(),foodProductRequest.getNutrition());
        foodProductService.addFoodProduct(foodProduct);
        return  ResponseEntity.ok(new MessageResponse("Product registered successfully!"));
    }

    @GetMapping("/deleteFoodProduct")
    public ResponseEntity<?> deleteFoodProduct(@RequestParam(name="id") int id) {
        foodProductService.deleteFoodProduct(id);
        return ResponseEntity.ok(new MessageResponse("Product deleted successfully!"));
    }

    @GetMapping("/get")
    public @ResponseBody FoodProduct getFoodProduct(@RequestParam(name="product_id")int id){
        return foodProductService.getFoodProduct(id);
    }

    @GetMapping("/getWithNutrition")
    public @ResponseBody FoodProduct getFoodProductNotLazy(@RequestParam(name="productId") int id) {
        FoodProduct foodProduct = foodProductService.getFoodProduct(id);
        foodProduct.getNutrition().getKcal();
        return foodProduct;
    }

    @GetMapping("/list")
    public  List<FoodProduct> displayAllFoodProducts(Model model) {
        List<FoodProduct> foodProducts = foodProductService.getFoodProducts();
        model.addAttribute("foodProducts", foodProducts);
        model.addAttribute("foodProduct", new FoodProduct());
        return foodProducts;
    }

}
