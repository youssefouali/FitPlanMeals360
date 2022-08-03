package com.example.mealplanner.controller;


import com.example.mealplanner.entity.FoodProduct;
import com.example.mealplanner.entity.Recipe;
import com.example.mealplanner.entity.User;
import com.example.mealplanner.payload.request.FoodProductRequest;
import com.example.mealplanner.payload.response.MessageResponse;
import com.example.mealplanner.security.services.UserDetailsServiceImpl;
import com.example.mealplanner.service.FileService;
import com.example.mealplanner.service.FoodProductService;
import com.example.mealplanner.service.RecipeService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private FoodProductService foodService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserDetailsServiceImpl userService;


    @GetMapping("/getAll")
    public @ResponseBody
    List<Recipe> getRecipes() {
        return recipeService.findAll();
    }

    @PostMapping("/addRecipe")
    public ResponseEntity<?> addRecipe(@Valid @RequestBody Recipe recipeRequest){
        //FoodProduct foodProduct = new FoodProduct(foodProductRequest.getName(),foodProductRequest.getFoodType(),foodProductRequest.getNutrition());
       // foodProductService.addFoodProduct(foodProduct);
        Recipe recipe = new Recipe(recipeRequest.getIngredients(),recipeRequest.getPreparation(),recipeRequest.getTitle(),
                recipeRequest.getDescription(),recipeRequest.getMealTypes(),recipeRequest.getImage(),
                recipeRequest.getServings());


        if (recipeRequest.getId() == 0) {
            User user = userService.getCurrentUser();
            recipe.setAuthor(user.getUsername());
            recipe.setOwner(user);
            if (recipe.getImage() == null) {
                recipe.setImage("/recipeImages/default.png");
            }
            if (userService.hasCurrentUserRole("ROLE_ADMIN")) {
                recipe.setInspected(true);
                recipe.setPublished(true);
            }
        }

        recipeService.save(recipe);

        return  ResponseEntity.ok(new MessageResponse("Recipe registered successfully!"));
    }

    @GetMapping("/delete")
    public String deleteRecipe(@RequestParam("recipeId") int id) {
        if (userService.hasCurrentUserRole("ROLE_ADMIN")) {
            recipeService.deleteById(id);
        } else {
            Recipe recipe = recipeService.findById(id);
            if (recipe.getOwner() == userService.getCurrentUser()) {
                recipeService.deleteById(id);
            }
        }
        return "Recipe deleted !!!";
    }

    @GetMapping("/list")
    public List<Recipe> listPublishedRecipes() {
        List<Recipe> recipes = recipeService.getPublicRecipes();
        return recipes;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/approveRecipe")
    public String makeRecipePublic(@RequestParam("recipeId") int recipeId) {
        User publisher = userService.getCurrentUser();
        recipeService.makeRecipePublic(recipeId, publisher);
        return "redirect:/recipe/sharedList";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/rejectRecipe")
    public String makeRecipePrivate(@RequestParam("recipeId") int recipeId) {
        Recipe recipe = recipeService.findById(recipeId);
        recipe.setInspected(true);
        recipe.setPublished(false);
        recipeService.save(recipe);
        return "redirect:/recipe/sharedList";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sharedList")
    public List<Recipe> listSharedRecipes() {
        List<Recipe> recipes = recipeService.getRecipesWaitingForInspection();

        return recipes;
    }



}
