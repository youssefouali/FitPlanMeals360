package com.example.mealplanner.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "dish")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Dish {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dish_id")
    private List<Ingredient> ingredients;

    public Dish() {

    }

    public Dish(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Dish(long id, List<Ingredient> ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public Nutrition getNutritionForDish() {
        List<Nutrition> nutritions = ingredients.stream().map(i -> i.getNutritionForIngredient())
                .collect(Collectors.toList());
        return Nutrition.sumNutritions(nutritions);
    }

 /*public List<Ingredient> cloneIngredients(List<Ingredient> ingredients) {
        List<Ingredient> copyOfIngredients = new ArrayList<Ingredient>();
        for (Ingredient ingredient : ingredients) {
            copyOfIngredients.add(new Ingredient(ingredient));
        }
        return copyOfIngredients;
    }*/

    public abstract String getTitle();

    public abstract int getServings();
}