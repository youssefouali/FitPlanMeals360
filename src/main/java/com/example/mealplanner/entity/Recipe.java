package com.example.mealplanner.entity;

import com.example.mealplanner.enums.MealType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "recipe")
public class Recipe extends Dish{

    @Id
    @GeneratedValue
    private long id;


    @Column
    private String title;

    @Column
    private String description;


    @Column
    @Enumerated(EnumType.STRING)
    private MealType mealTypes;

    @Column
    private String image;

    @Column
    private boolean shared;

    // approved by admin
    @Column
    private boolean inspected;

    // is public
    @Column
    private boolean published;

    @Column
    private String author;

    @Column
    private int servings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preparation_id", referencedColumnName = "id")
    private Preparation preparation;

    public Recipe() {
    }

    public Recipe(List<Ingredient> ingredients,Preparation preparation, String title, String description, MealType mealTypes, String image, boolean shared, boolean inspected, boolean published, String author, int servings, User owner) {
        super(ingredients);
        this.preparation=preparation;
        this.title = title;
        this.description = description;
        this.mealTypes = mealTypes;
        this.image = image;
        this.shared = shared;
        this.inspected = inspected;
        this.published = published;
        this.author = author;
        this.servings = servings;
        this.owner = owner;
    }


    public Recipe(List<Ingredient> ingredients, Preparation preparation, String title, String description, MealType mealTypes, String image, int servings) {
        super(ingredients);
        this.preparation=preparation;
        this.title = title;
        this.description = description;
        this.mealTypes = mealTypes;
        this.image = image;
        this.servings = servings;
    }

    public Recipe(Recipe recipe) {
    }
}
