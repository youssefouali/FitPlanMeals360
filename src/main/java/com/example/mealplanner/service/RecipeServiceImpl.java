package com.example.mealplanner.service;

import com.example.mealplanner.entity.Ingredient;
import com.example.mealplanner.entity.KitchenProduct;
import com.example.mealplanner.entity.Recipe;
import com.example.mealplanner.entity.User;
import com.example.mealplanner.repos.DishRepository;
import com.example.mealplanner.repos.FoodProductRepository;
import com.example.mealplanner.repos.RecipeRepository;
import com.example.mealplanner.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService{

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    FoodProductRepository foodProductRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DishRepository dishRepository;


    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe findById(long id) {
        Optional<Recipe> result = recipeRepository.findById(id);
        Recipe recipe = null;
        if (result.isPresent()) {
            recipe = result.get();
        } else {
            logger.error("Did not find recipe id - " + id);
            throw new RuntimeException("Did not find recipe id - " + id);
        }
        return recipe;
    }

    @Override
    public void save(Recipe recipe) {
        recipeRepository.save(recipe);

    }

    @Override
    public void deleteById(long id) {

        recipeRepository.deleteById(id);

    }

    @Override
    public Recipe findByTitle(String title) {
        return recipeRepository.findByTitle(title);
    }

    @Override
    public List<Recipe> getPublicRecipes() {
        return recipeRepository.findByPublished(true);
    }

    @Override
    public void makeRecipePublic(int recipeId, User publisher) {
        Recipe recipe = findById(recipeId);
        recipe.setInspected(true);
        createPublicRecipeCopy(recipe, publisher);
        recipeRepository.save(recipe);
    }

    private void createPublicRecipeCopy(Recipe recipe, User publisher) {
        Recipe copyRecipe = new Recipe(recipe);
        copyRecipe.setOwner(publisher);
        copyRecipe.setPublished(true);
        recipeRepository.save(copyRecipe);
    }

    @Override
    public void makeRecipePrivate(long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).get();
        recipe.setInspected(true);
        recipe.setPublished(false);
        recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getRecipesWaitingForInspection() {
        return recipeRepository.findBySharedAndInspected(true, false);
    }

    @Override
    public List<Recipe> getRecipesForUserProducts(List<KitchenProduct> products) {
        List<Recipe> recipes = recipeRepository.findAll();
        List<Recipe> availableRecipes = new ArrayList<Recipe>();
        for (Recipe recipe : recipes) {
            if (areIngredientsInKitchen(recipe.getIngredients(), products)) {
                availableRecipes.add(recipe);
            }
        }
        return availableRecipes;
    }

    private boolean areIngredientsInKitchen(List<Ingredient> ingredients, List<KitchenProduct> products) {
        for (Ingredient ingredient : ingredients) {
            if (!isIngredientInKitchen(ingredient, products)) {
                return false;
            }
        }
        return true;
    }

    private boolean isIngredientInKitchen(Ingredient ingredient, List<KitchenProduct> products) {
        for (KitchenProduct product : products) {
            if (ingredient.getFoodProduct().equals(product.getFoodProduct())) {
                return true;
            }
        }
        return false;
    }

}
