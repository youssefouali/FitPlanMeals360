package com.example.mealplanner.controller;


import com.example.mealplanner.entity.*;
import com.example.mealplanner.enums.MealType;
import com.example.mealplanner.enums.UnitType;
import com.example.mealplanner.exception.DuplicateDishInMealException;
import com.example.mealplanner.exception.OverlappingPlanDatesExceptions;
import com.example.mealplanner.payload.request.ShoppingItemDTO;
import com.example.mealplanner.security.services.UserDetailsServiceImpl;
import com.example.mealplanner.service.FoodProductService;
import com.example.mealplanner.service.KitchenService;
import com.example.mealplanner.service.PlanService;
import com.example.mealplanner.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/plan")
public class PlanController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    KitchenService kitchenService;

    @Autowired
    PlanService planService;

    @Autowired
    FoodProductService foodProductService;

    Logger logger = LoggerFactory.getLogger(getClass());



    @PostMapping("/createPlan")
    public String saveNewPlan(@RequestBody Plan planRequest ,String copyPlan, Long planId, RedirectAttributes redirectAttrs) {
        try {
            Plan newPlan = new Plan(planRequest.getTitle(),planRequest.getStartDate(),planRequest.getEndDate());
            //newPlan.setTitle(title);
            //String[] datesArray = dates.split(" - ");
            //newPlan.setStartDate(startDate);
            //newPlan.setEndDate(endDate);
            newPlan.setUser(userService.getCurrentUser());
            logger.info("Creating plan name: " + newPlan.getTitle() + ", from: " + newPlan.getStartDate() + ", to: "
                    + newPlan.getEndDate() + ", for user with id: ", newPlan.getUser().getId());
            /*if (copyPlan.contentEquals("old")) {
                Plan planWithMeals = planService.getPlanById(planId);
                planService.copyPlanMealsToPlan(planWithMeals, newPlan);
            }*/
            planService.save(newPlan);
            logger.info("Plan created with id: " + newPlan.getId());
            return "redirect:/plan/meals?id=" + newPlan.getId();
        } catch (OverlappingPlanDatesExceptions e) {
            logger.warn("User already has a plan for specified dates");
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/plan/list";
        }
    }

    @GetMapping("/list")
    public String getUserPlans(@ModelAttribute String errorMessage) {
        User user = userService.getCurrentUser();
        List<Plan> previousPlans = planService.getAllUserPlansUntil(user.getId(), LocalDate.now());
        List<Plan> upcomingPlans = planService.getAllUserPlansFrom(user.getId(), LocalDate.now());
        return("Number of user plans found: old - " + previousPlans.size() + ", new - " + upcomingPlans.size());
        //model.addAttribute("previousPlans", previousPlans);
       // model.addAttribute("upcomingPlans", upcomingPlans);
       // if (!errorMessage.isEmpty()) {
            //model.addAttribute("errorMessage", errorMessage);
      //  }
       // return "plans";
    }

    @GetMapping("/meals")
    public String showPlan(@ModelAttribute String errorMessage, @RequestParam long planId, Model model) {
        User user = userService.getCurrentUser();
        Plan plan = planService.getPlanById(planId);
        for (Meal meal : plan.getMeals()) {
            Collections.sort(meal.getMealDishes());
        }
        model.addAttribute("planStyle", user.getPlanStyle());
        model.addAttribute("mealTypes", MealType.values());
        model.addAttribute("unitTypes", UnitType.values());
        model.addAttribute("plan", plan);
        model.addAttribute("dailyNutrition", plan.getIndividualDatesNutrition());
        return "plan-meals";
    }

    @PostMapping("/addDish")
    public String addDishToMeal(Integer recipeId, Integer foodProductId, String ammount, String unitType, String date,
                                String mealType, Integer servings, Long planId, RedirectAttributes redirectAttrs) {
        MealType mealTypeVal = MealType.valueOf(mealType);;
        LocalDate mealDate = LocalDate.parse(date);
        // get meal if exists by plan id, date and mealType
        Meal meal = planService.getMeal(planId, mealDate, mealTypeVal);
        // if meal doesn't exist create new
        if (meal == null) {
            Plan plan = planService.getPlanById(planId);
            meal = new Meal(0, new ArrayList<MealDish>(), mealTypeVal, mealDate, plan);
        }
        try {
            if (recipeId != null) {
                Dish dish = recipeService.findById(recipeId);
                planService.addDishToMeal(meal, dish, servings);
            } else if (foodProductId != null) {
                UnitType ingrUnitType = UnitType.valueOf(unitType);
                FoodProduct foodProduct = foodProductService.getFoodProduct(foodProductId);
                Ingredient ingredient = new Ingredient(0, ammount, ingrUnitType, foodProduct);
                SingleDishProduct dish = new SingleDishProduct();
                dish.setIngredient(ingredient);
                planService.saveSingleDish(dish);
                planService.addDishToMeal(meal, dish, servings);
            }
        } catch (DuplicateDishInMealException e) {
            logger.warn("User already has this dish in meal the same date and same type");
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/plan/meals?id=" + planId;
        }
        logger.info("New dish added to meal(id: " + meal.getId() + ") in plan(id: " + planId + ")");
        return "redirect:/plan/meals?id=" + planId;
    }

    @GetMapping("/removeRecipe")
    public String removeRecipeFromMeal(@RequestParam("mealId") int mealId, @RequestParam("recipeId") int recipeId) {
        Meal meal = planService.getMeal(mealId);
        Recipe recipe = recipeService.findById(recipeId);
        planService.removeDishFromMeal(meal, recipe);
        Plan plan = planService.getPlanByMealId(mealId);
        return "redirect:/plan/meals?id=" + plan.getId();
    }
    @GetMapping("/removeSingleDish")
    public String removeSingleDish(@RequestParam("mealId") int mealId, @RequestParam("singleDishId") int singleDishId) {
        Meal meal = planService.getMeal(mealId);
        SingleDishProduct singleDishProduct = planService.getSingleDishProduct(singleDishId);
        planService.removeDishFromMeal(meal, singleDishProduct);
        planService.deleteSingleDish(singleDishProduct);
        Plan plan = planService.getPlanByMealId(mealId);
        return "redirect:/plan/meals?id=" + plan.getId();
    }

    @GetMapping("/getMeal")
    public @ResponseBody Meal getMeal(@RequestParam("mealId") int id) {
        return planService.getMeal(id);
    }

    @GetMapping("/getSingleDish")
    public @ResponseBody SingleDishProduct getSingleDish(@RequestParam("productId") int id) {
        SingleDishProduct singleDish = planService.getSingleDishProduct(id);
        singleDish.getIngredients().size();
        return singleDish;
    }

    @GetMapping("/getMealDish")
    public @ResponseBody MealDish getMealDish(@RequestParam("mealId") int mealId,
                                              @RequestParam("recipeId") Integer recipeId) {
        Meal meal = planService.getMeal(mealId);
        Recipe recipe = recipeService.findById(recipeId);
        return meal.findMealDish(recipe);
    }

    @GetMapping("/getMealsForToday")
    public @ResponseBody List<Meal> getMealsForToday() {
        User user = userService.getCurrentUser();
        Plan plan = planService.getCurrentPlan(user);
        return plan.getMealsForToday();
    }

    @GetMapping("/getShoppingItems")
    public @ResponseBody
    Map<String, List<ShoppingItemDTO>> getShoppingItems(@RequestParam Long planId) {
        User user = userService.getCurrentUser();
        Plan plan = planService.getPlanById(planId);
        Map<String, List<ShoppingItemDTO>> shoppingList = planService.getPreparedShoppingList(planId);
        return shoppingList;
    }

    @PostMapping("/updateShoppingItem")
    public @ResponseBody String updateShoppingItem(@RequestParam int planId, @RequestParam String ingredientName,
                                                   @RequestParam boolean isDone, @RequestParam String units) {
        planService.updateShoppingItems(planId, ingredientName, isDone, UnitType.valueOf(units));
        return "updated";
    }










}
